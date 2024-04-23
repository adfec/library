package co.ximple.library.api;

import co.ximple.library.dto.ItemDto;
import co.ximple.library.service.ISearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SearchController {

  private final ISearchService searchService;

  public SearchController(ISearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping("/v1/search")
  public ResponseEntity<List<ItemDto>> searchBook(
      @RequestParam(name = "criteria") Optional<String> criteria) {
    if (criteria.isPresent()) {
      return new ResponseEntity<>(searchService.find(criteria.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
