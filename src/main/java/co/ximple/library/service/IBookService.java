package co.ximple.library.service;

import co.ximple.library.dto.ResponseDto;
import co.ximple.library.dto.ReviewDto;

import java.util.List;

public interface IBookService {
  List<ReviewDto> getReviewsByBook(Long bookId, String filter);

  ResponseDto createReviewByBook(ReviewDto reviewDto);
}
