package co.ximple.library.service.impl;

import co.ximple.library.domain.Item;
import co.ximple.library.dto.BookingDto;
import co.ximple.library.dto.ResponseDto;
import co.ximple.library.entities.Book;
import co.ximple.library.entities.Booking;
import co.ximple.library.entities.ItemHolds;
import co.ximple.library.repository.BookRepository;
import co.ximple.library.repository.BookingRepository;
import co.ximple.library.repository.ItemHoldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemHoldRepository itemHoldRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingDto bookingDto;
    private Long itemId = 1L;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @BeforeEach
    void setUp() {
        bookingDto = new BookingDto(1L, "BOOK", 1, "HOLD");
    }

    @Test
    void createBooking_Successful() {
        when(bookRepository.findById(itemId)).thenReturn(Optional.of(Book.builder().copies(3).build()));
        when(itemHoldRepository.findByItemId(itemId)).thenReturn(ItemHolds.builder().loans(1).build());
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking());
        when(itemHoldRepository.save(any(ItemHolds.class))).thenReturn(new ItemHolds());
        ResponseDto result = bookingService.createBooking(itemId, bookingDto);
        assertEquals(HttpStatus.CREATED.value(), result.code());
        assertEquals("Booking successful", result.cause());
    }

    @Test
    void createBooking_ItemDoesNotExist() {
        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        ResponseDto result = bookingService.createBooking(itemId, bookingDto);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.code());
        assertEquals("The requested item does not exist", result.cause());
    }

    @Test
    void createBooking_NotEnoughQuantity() {
        when(bookRepository.findById(any())).thenReturn(Optional.ofNullable(Book.builder().copies(0).build()));
        when(itemHoldRepository.findByItemId(any())).thenReturn(new ItemHolds(1L,0));
        ResponseDto result = bookingService.createBooking(itemId, bookingDto);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.code());
        assertEquals("The requested item quantity is not available", result.cause());
    }
}
