package co.ximple.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReview {
    @Id
    @GenericGenerator(name="keyReview", strategy="increment")
    @GeneratedValue(generator="keyReview")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private Date reviewDate;
    @ManyToOne
    @JoinColumn(name = "user_nid")
    private Users usersReview;
    private String comment;
    private Integer review;
}
