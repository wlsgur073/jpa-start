package EmbeddedType;

import jakarta.persistence.*;
import jpabook.japshop.domain.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MemberEm {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    // Period
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
    @Embedded // 값 타입을 사용하는 곳에 표시
    private Period workPeriod;

    // Address
//    private String city;
//    private String street;
//    private String zipcode;

    @Embedded
    private Address homeAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
