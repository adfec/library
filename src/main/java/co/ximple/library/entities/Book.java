package co.ximple.library.entities;

import co.ximple.library.domain.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Item {
  private String marc;
  @OneToMany
  @JoinColumn(name = "book_id")
  private List<BookReview> bookReview;
}
