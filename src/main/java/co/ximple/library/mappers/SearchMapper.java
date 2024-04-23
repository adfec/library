package co.ximple.library.mappers;

import co.ximple.library.dto.ItemDto;
import co.ximple.library.entities.ItemSearch;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Mapper
public interface SearchMapper {
  SearchMapper INSTANCE = Mappers.getMapper(SearchMapper.class);

  @Mapping(source = "authors", target = "authors")
  @Mapping(source = "itemid", target = "itemId")
  @Mapping(source = "codenumber", target = "codeNumber")
  ItemDto searchToDto(ItemSearch itemSearch);

  default Map<String, String> parseJsonString(String jsonString) {
    try {
      var objectMapper = new ObjectMapper();
      return objectMapper.readValue(jsonString, new TypeReference<Map<String, String>>() {});
    } catch (IOException e) {
      return Collections.emptyMap();
    }
  }
}
