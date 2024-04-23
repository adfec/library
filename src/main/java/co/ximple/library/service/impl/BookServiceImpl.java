package co.ximple.library.service.impl;

import co.ximple.library.dto.ResponseDto;
import co.ximple.library.dto.ReviewDto;
import co.ximple.library.entities.Book;
import co.ximple.library.entities.BookReview;
import co.ximple.library.entities.Users;
import co.ximple.library.mappers.ReviewMapper;
import co.ximple.library.repository.BookRepository;
import co.ximple.library.repository.ReviewRepository;
import co.ximple.library.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements IBookService {

  private final BookRepository bookRepository;
  private final ReviewRepository reviewRepository;

  public BookServiceImpl(BookRepository bookRepository, ReviewRepository reviewRepository) {
    this.bookRepository = bookRepository;
    this.reviewRepository = reviewRepository;
  }

  @Cacheable(value = "reviewCache")
  @Override
  public List<ReviewDto> getReviewsByBook(Long bookId, String filter) {
    Optional<Book> book = bookRepository.findById(bookId);
    if (book.isPresent()) {
      return book.get().getBookReview().stream()
          .map(review -> ReviewMapper.INSTANCE.reviewToDto(review))
          .collect(Collectors.toList());
    }
    return Collections.EMPTY_LIST;
  }

  @Override
  public ResponseDto createReviewByBook(ReviewDto reviewDto) {
    ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "Review created successfully");
    try {
      BookReview bookReview =
          BookReview.builder()
              .book(Book.builder().id(Long.valueOf(reviewDto.bookId())).build())
              .review(reviewDto.rate())
              .comment(reviewDto.review())
              .usersReview(Users.builder().nid(reviewDto.userId()).build())
              .reviewDate(new Date())
              .build();
      reviewRepository.save(bookReview);
    } catch (Exception e) {
      log.error("[Error {}] Cause - {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
      responseDto =
          new ResponseDto(
              HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while trying to create the review");
    }
    return responseDto;
  }
}
