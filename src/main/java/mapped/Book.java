package mapped;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("B") // default 값은 엔티티 명이다.
public class Book extends ItemMapped {

    private String author;
    private String isbn;
}
