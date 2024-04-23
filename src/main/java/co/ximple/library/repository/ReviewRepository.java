package co.ximple.library.repository;

import co.ximple.library.entities.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<BookReview, Long> {}
