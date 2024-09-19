package jpabook.japshop.domain;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class Delivery {
    @Id
    @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipcode;
    private DeliveyStatus status;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;
}
