package jpql.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team2 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /*
    * Batch Size는 글로벌 설정으로 모든 query에 적용시킬수도 있다. (persistence.xml 참고).
    * <property name="hibernate.default_batch_fetch_size" value="100" />
    * */
    @BatchSize(size = 100) // LAZY 처리 중인 것에 batchSize를 줘서 청크 단위로 sql를 보낼 수 있다.
    @OneToMany(mappedBy = "team")
    private List<Member2> member2s = new ArrayList<>();

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

}
