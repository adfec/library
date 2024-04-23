package co.ximple.library.service.impl;

import co.ximple.library.domain.Item;
import co.ximple.library.dto.BookingDto;
import co.ximple.library.dto.ResponseDto;
import co.ximple.library.entities.Book;
import co.ximple.library.entities.Booking;
import co.ximple.library.entities.ItemHolds;
import co.ximple.library.entities.Users;
import co.ximple.library.repository.BookRepository;
import co.ximple.library.repository.BookingRepository;
import co.ximple.library.repository.ItemHoldRepository;
import co.ximple.library.service.IBookingService;
import co.ximple.library.utils.ItemTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

import static java.lang.StringTemplate.STR;

@Service
@Slf4j
public class BookingServiceImpl implements IBookingService {

  private final BookingRepository bookingRepository;
  private final ItemHoldRepository itemHoldRepository;
  private final BookRepository bookRepository;
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public BookingServiceImpl(
          BookingRepository bookingRepository, ItemHoldRepository itemHoldRepository, BookRepository bookRepository) {
    this.bookingRepository = bookingRepository;
    this.itemHoldRepository = itemHoldRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public ResponseDto createBooking(Long itemId, BookingDto bookingDto) {
    ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED.value(), "Booking successful");
    try {
      lock.readLock().lock();
      Optional<Item> item = this.getItemByType(itemId, bookingDto.type());
      if (!item.isPresent()) {
        return new ResponseDto(HttpStatus.BAD_REQUEST.value(), "The requested item does not exist");
      }
      var availableLoans = this.amountLoans(item.get(), bookingDto.amount());
      if (availableLoans > 0) {
        Booking booking =
            Booking.builder()
                .users(Users.builder().nid(bookingDto.userId()).build())
                .itemId(itemId)
                .build();
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()){
          Supplier<Booking> saveBooking = scope.fork(() -> bookingRepository.save(booking));
          Supplier<ItemHolds> saveHols = scope.fork(() -> itemHoldRepository.save(ItemHolds.builder().itemId(itemId).loans(availableLoans).build()));
          scope.join();
        }
      } else {
        responseDto = new ResponseDto(HttpStatus.NOT_FOUND.value(), "The requested item quantity is not available");
      }
    } catch (Exception e) {
      log.error("[Error {}] Cause - {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
      responseDto =
          new ResponseDto(
              HttpStatus.INTERNAL_SERVER_ERROR.value(),
              STR."Error while trying to create the reservation for the item \{itemId}");
    } finally{
      lock.readLock().unlock();
    }
    return responseDto;
  }

  private <T extends Item> Integer amountLoans(T itemType, Integer amountRequested) {
    try{
      lock.readLock().lock();
      if (amountRequested < 1) {
        return 0;
      }
      ItemHolds itemLoans = itemHoldRepository.findByItemId(itemType.getId());
      if (itemLoans == null) {
        var quantity = Math.subtractExact(itemType.getCopies(), amountRequested);
        if (quantity >= 0) {
          return amountRequested;
        } else {
          return 0;
        }
      }
      if (Math.addExact(itemLoans.getLoans(),amountRequested) <= itemType.getCopies()) return Math.addExact(itemLoans.getLoans(),amountRequested);
    } catch (Exception e) {
      log.error("[Error {}] Cause - {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    } finally{
      lock.readLock().unlock();
    }
    return 0;
  }

  private Optional<Item> getItemByType(Long itemId, String type) {
    Optional<Item> item = Optional.empty();
    if (type.equalsIgnoreCase(ItemTypes.BOOK.toString())) {
      Optional<Book> book = bookRepository.findById(itemId);
      if (book.isPresent()) {
        item = Optional.ofNullable(Book.builder().id(itemId).copies(book.get().getCopies()).build());
      }
    }
    return item;
  }
}
