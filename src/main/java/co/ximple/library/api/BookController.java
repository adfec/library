package co.ximple.library.api;

import co.ximple.library.dto.ResponseDto;
import co.ximple.library.dto.ReviewDto;
import co.ximple.library.service.IBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

  private final IBookService bookService;

  public BookController(IBookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/v1/review/{bookId}")
  public ResponseEntity<List<ReviewDto>> searchReviewByBook(
      @PathVariable("bookId") Optional<Long> bookId,
      @RequestParam(value = "list") String limit) {
    if (bookId.isEmpty()) {
      ResponseEntity.badRequest();
    }
    return new ResponseEntity<>(bookService.getReviewsByBook(bookId.get(), limit), HttpStatus.OK);
  }

  @PostMapping("/v1/review")
  public ResponseEntity<ResponseDto> createReviewByBook(@RequestBody ReviewDto reviewDto) {
    ResponseDto reviewByBook = bookService.createReviewByBook(reviewDto);
    return new ResponseEntity<>(reviewByBook, HttpStatusCode.valueOf(reviewByBook.code()));
  }
}
