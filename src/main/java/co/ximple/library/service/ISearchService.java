package co.ximple.library.service;

import co.ximple.library.dto.ItemDto;

import java.util.List;

public interface ISearchService {

  List<ItemDto> find(String criteria);
}
