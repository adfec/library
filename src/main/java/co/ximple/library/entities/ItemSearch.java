package co.ximple.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ItemSearch {

  @Id private Integer itemid;
  private String code;
  private String codenumber;
  private String format;
  private String title;
  private String authors;
  private Integer edition;
  private Integer rating;
  private Integer availability;
}
