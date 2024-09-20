package valueType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaTester2 {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            MemberCollection member = new MemberCollection();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "zipcode"));
            member.getAddressHistory().add(new Address("old2", "street", "zipcode"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("========================= START =========================");
            MemberCollection findMemeber = em.find(MemberCollection.class, member.getId());

            /* Collection type 들은 모두 지연로딩으로 동작한다. */
            /*List<Address> addressHistory = findMemeber.getAddressHistory();
            for (Address a: addressHistory) {
                System.out.println("address city = " + a.getCity());
            }*/


            // homeCity -> newCity 변경이 필요한 경우
            // findMemeber.getHomeAddress().setCity("newCity"); 이렇게 하면 안된다.
            // 새로운 객체를 집어 넣어야 한다.
            Address a = findMemeber.getHomeAddress();
            findMemeber.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 값 타입 컬랙션 값을 변경하고 싶을 경우
            // 치킨 -> 한식
            findMemeber.getFavoriteFoods().remove("치킨");
            findMemeber.getFavoriteFoods().add("한식");

            // old1 -> oooooold1 // 컬랙션은 기본적으로 equals()로 값을 찾기에 객체를 참조할 수 있게 해야한다.
            findMemeber.getAddressHistory().remove(new Address("old1", "street", "zipcode"));
            findMemeber.getAddressHistory().add(new Address("ooooooold1", "street", "zipcode"));

            // 가장 좋은 방법은 address 따위를 엔티티로 바꿔서 그 row마다의 id 값을 참조해서 해당 엔티티의 값을 초기화해주는 방법이 좋다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
