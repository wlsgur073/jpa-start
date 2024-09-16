package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class MappingTeam {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<MappingMember> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String team_name) {
        this.name = team_name;
    }

    public List<MappingMember> getMembers() {
        return members;
    }

    public void setMembers(List<MappingMember> members) {
        this.members = members;
    }
}
