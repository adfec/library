package co.ximple.library.dto;

public record ReviewDto(Long userId, String alias, Integer bookId, Integer rate, String review) {}