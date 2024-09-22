package jpql;

import jakarta.persistence.*;
import jpql.domain.Member2;
import jpql.domain.MemberDTO;

import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            Member2 member = new Member2();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            // 반환 타입이 명확할 때 사용
//            TypedQuery<Member2> query1 = em.createQuery("select m from Member2 m", Member2.class);

            // 반환 타입이 명확하지 않을 때 사용(username은 string, age는 int라서 type이 서로 다른 경우)
//            Query query2 = em.createQuery("select m.username, m.age from Member2 m");

            /* 결과 조회 API */
//             query1.getResultList();   // 복수 반환할 경우 - 결과가 없으면 empty 리스트를 반환
//             단일 반환인 경우 - 결과가 정확히 1개이지 않으면 Exception 발생(try-catch 필요)
//             query1.getSingleResult();

            /* 파라미터 바인딩 */
//            em.createQuery("select m from Member2 m where m.username = :username", Member2.class)
//                    .setParameter("username", "member1");
//            em.createQuery("select m from Member2 m where m.username = ?1", Member2.class)
//                    .setParameter(1, "member1"); // 순서대로 작성해야 되기에 유지보수가 어렵기에 비추천

            /* 프로젝션 */
//            List<Team2> result = em
                    // 묵시적 조인을 통해 해당 jpql도 jpa가 알아서 team을 join해서 가져오지만
                    // 가독성과 팀 전체를 위해서라도 명시적 조인으로 코딩해주는 것이 좋다.
//                    .createQuery("select m.team from Member2 m join m.team t", Team2.class)

                    // 임베디드 타입은 소속된 엔티티에서 꺼내올 수 있다.
//                    .createQuery("select o.address2 from Order2 o", Address2.class)

//                    .createQuery("select m.team from Member2 m", Team2.class)
//                    .getResultList();

            // 여러 값 조회 - 해당 구분은 queryDSL에서 개선된 사항으로 사용된다.
            List<MemberDTO> memberDTOS = em.createQuery(
                    "select new jpql.domain.MemberDTO(m.username, m.age) from Member2 m"
                            , MemberDTO.class)
                    .getResultList();
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
