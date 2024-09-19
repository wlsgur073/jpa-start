package hellojpa;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker", fetch = LAZY)
    private MappingMember member;
}
