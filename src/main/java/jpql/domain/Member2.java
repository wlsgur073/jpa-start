package jpql.domain;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
@NamedQuery( // 어노테이션이기에 애플리케이션 로딩 시점에 query를 미리 검증해주는 장점이 있다.
        name = "Member.findByUsername"
        , query = "select m from Member2 m where m.username = :username"
)
public class Member2 {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM2_ID")
    private Team2 team;

    private String username;

    private int age;

    public Member2() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team2 getTeam() {
        return team;
    }

    public void setTeam(Team2 team) {
        this.team = team;
    }
}
