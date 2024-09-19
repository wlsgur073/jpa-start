package hellojpa;

import jakarta.persistence.*;

@Entity
public class MemberProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private MappingMember member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    private int count;
    private int price;

    // @ManyToMany -> @OneToMany, @ManyToOne 으로 바꿔서 사용한다.
}
