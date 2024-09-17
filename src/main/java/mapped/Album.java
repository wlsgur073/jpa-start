package mapped;

import jakarta.persistence.Entity;

@Entity
public class Album extends ItemMapped {

    private String artist;
}
