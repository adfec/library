package co.ximple.library.service.strategy;

import co.ximple.library.dto.ItemDto;
import co.ximple.library.entities.ItemSearch;
import co.ximple.library.mappers.SearchMapper;
import co.ximple.library.repository.SearchViewRepository;
import co.ximple.library.service.ISearchStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("view")
public class SearchViewStrategy implements ISearchStrategy {

  private final SearchViewRepository searchRepository;

  public SearchViewStrategy(SearchViewRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  @Override
  public List<ItemDto> find(String criteria) {
    List<ItemSearch> contentByCriteria = searchRepository.findContentByCriteria(criteria);
    return contentByCriteria.stream()
        .map(itemSearch -> SearchMapper.INSTANCE.searchToDto(itemSearch))
        .collect(Collectors.toList());
  }
}
