package co.ximple.library.api;

import co.ximple.library.dto.BookingDto;
import co.ximple.library.dto.ResponseDto;
import co.ximple.library.service.IBookingService;
import co.ximple.library.utils.ItemStates;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

  private final IBookingService bookingService;

  public BookingController(IBookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping("/v1/booking")
  public ResponseEntity<ResponseDto> createNewBooking(
      @RequestParam(name = "id") Long itemId, @RequestBody BookingDto bookingDto) {
    if (bookingDto.state().equalsIgnoreCase(ItemStates.HOLD.toString())) {
      ResponseDto booking = bookingService.createBooking(itemId, bookingDto);
      return new ResponseEntity<>(booking, HttpStatusCode.valueOf(booking.code()));
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
