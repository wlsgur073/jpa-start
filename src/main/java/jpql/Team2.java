package jpql;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team2 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member2> member2s = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member2> getMember2s() {
        return member2s;
    }

    public void setMember2s(List<Member2> member2s) {
        this.member2s = member2s;
    }

    public Address2 getAddress() {
        return address2;
    }

    public void setAddress(Address2 address2) {
        this.address2 = address2;
    }
}
