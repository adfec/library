package co.ximple.library.service;

import co.ximple.library.dto.BookingDto;
import co.ximple.library.dto.ResponseDto;

public interface IBookingService {

  ResponseDto createBooking(Long itemId, BookingDto bookingDto);
}
