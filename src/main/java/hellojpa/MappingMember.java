package hellojpa;

import jakarta.persistence.*;

@Entity
public class MappingMember {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private MappingTeam team;

    @OneToOne
    @JoinColumn(name = "LOCEK_ID")
    private Locker locker;

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
