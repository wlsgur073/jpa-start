package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class MappingMember {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private MappingTeam team;

    @OneToOne
    @JoinColumn(name = "LOCEK_ID")
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "MEMBER_PRODUCT") // 실무에서는 다대다를 쓰지 않고 중간 테이블을 엔티티로 새로 설정해준다.
//    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MappingTeam getTeam() {
        return team;
    }

    public void changeTeam(MappingTeam team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
