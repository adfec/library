package co.ximple.library.repository;

import co.ximple.library.entities.ItemSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchViewRepository extends JpaRepository<ItemSearch, Integer> {
  @Query(
      nativeQuery = true,
      value =
          """
            SELECT
            itemid,
            code,
            codenumber,
            format,
            title,
            authors,
            edition,
            rating,
            availability
            FROM V_SEARCH WHERE UPPER(criteria) LIKE UPPER(CONCAT('%',:criteria,'%'))""")
  List<ItemSearch> findContentByCriteria(@Param("criteria") String criteria);
}
