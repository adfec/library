package co.ximple.library.dto;

import java.io.Serializable;
import java.util.Map;

public record ItemDto(
    Integer itemId,
    String code,
    String codeNumber,
    String format,
    String title,
    Map<String, String> authors,
    Integer edition,
    Integer rating,
    Integer availability) implements Serializable {}
