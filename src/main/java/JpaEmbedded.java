import EmbeddedType.Address;
import EmbeddedType.MemberEm;
import EmbeddedType.Period;
import hellojpa.MappingMember;
import hellojpa.MappingTeam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaEmbedded {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            Address address = new Address("city", "street", "zipcode");


            MemberEm member1 = new MemberEm();
            member1.setHomeAddress(address);
            member1.setWorkPeriod(new Period());
            em.persist(member1);


            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            MemberEm member2 = new MemberEm();
//            member2.setHomeAddress(address);
            member2.setWorkPeriod(new Period());

            // 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 부작용(side effect) 발생
            member1.getHomeAddress().setCity("newCity"); // member1만 변경되길 의도했으나 member2도 같이 변경됨

            // 따라서 예를들어, address를 또 이용하고 싶다면 new Address를 해서 복사한 객체를 이식해줘야 함
            member2.setHomeAddress(copyAddress);
            em.persist(member2);

            /*
            * 그러나 매번 위처럼 사용하기에 실수가 발생할 수 있음으로 불변 객체(immutable object)로 설계하여
            * 생성되는 시점에 절대 값을 변경할 수 없게 만들어야 함. Setter를 지양해라.
            * */

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
