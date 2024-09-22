package jpql.domain;

import jakarta.persistence.*;

@Entity
public class Order2 {
    @Id
    @GeneratedValue
    private Long id;

    private int orderAmount;

    @Embedded
    private Address2 address2;

    @ManyToOne
    @JoinColumn(name = "PRODUCT2_ID")
    private Product2 product2;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Address2 getAddress2() {
        return address2;
    }

    public void setAddress2(Address2 address2) {
        this.address2 = address2;
    }

    public Product2 getProduct2() {
        return product2;
    }

    public void setProduct2(Product2 product2) {
        this.product2 = product2;
    }
}
