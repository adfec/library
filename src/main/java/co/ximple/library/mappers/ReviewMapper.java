package co.ximple.library.mappers;

import co.ximple.library.dto.ReviewDto;
import co.ximple.library.entities.BookReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(source = "usersReview.nid", target = "userId")
    @Mapping(source = "usersReview.userAlias", target = "alias")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "review", target = "rate")
    @Mapping(source = "comment", target = "review")
    ReviewDto reviewToDto(BookReview review);
}
