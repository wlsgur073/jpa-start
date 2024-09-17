package mapped;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략
@DiscriminatorColumn
public abstract class ItemMapped {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    // TABLE_PER_CLASS 전략의 경우에는 dtype를 사용하지 않는다.
    // 그러나 비효율적이기에 해당 전략은 비추천
//    private String dtype;

    public ItemMapped() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
