package co.ximple.library.service.strategy;

import co.ximple.library.dto.ItemDto;
import co.ximple.library.service.ISearchStrategy;

import java.util.List;

public class SearchIndexStrategy implements ISearchStrategy {
    @Override
    public List<ItemDto> find(String criteria) {
        return null;
    }
}
