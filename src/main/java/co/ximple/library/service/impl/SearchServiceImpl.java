package co.ximple.library.service.impl;

import co.ximple.library.dto.ItemDto;
import co.ximple.library.service.ISearchService;
import co.ximple.library.service.ISearchStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {
  private final ISearchStrategy searchStrategy;

  public SearchServiceImpl(@Qualifier("view") ISearchStrategy searchStrategy) {
    this.searchStrategy = searchStrategy;
  }

  @Cacheable(value = "searchCache")
  @Override
  public List<ItemDto> find(String criteria) {
    return searchStrategy.find(criteria);
  }
}
