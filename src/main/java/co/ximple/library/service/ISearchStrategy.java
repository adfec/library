package co.ximple.library.service;

import co.ximple.library.dto.ItemDto;

import java.util.List;

public interface ISearchStrategy {
    List<ItemDto> find(String criteria);
}
