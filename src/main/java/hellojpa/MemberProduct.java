package hellojpa;

import jakarta.persistence.*;

@Entity
public class MemberProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private MappingMember member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;

    // @ManyToMany -> @OneToMany, @ManyToOne 으로 바꿔서 사용한다.
}
