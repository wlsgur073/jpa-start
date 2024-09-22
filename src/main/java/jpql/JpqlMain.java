package jpql;

import jakarta.persistence.*;

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
            TypedQuery<Member2> query1 = em.createQuery("select m from Member2 m", Member2.class);

            // 반환 타입이 명확하지 않을 때 사용(username은 string, age는 int라서 type이 서로 다른 경우)
            Query query2 = em.createQuery("select m.username, m.age from Member2 m");

            /* 결과 조회 API */
//             query1.getResultList();   // 복수 반환할 경우 - 결과가 없으면 empty 리스트를 반환
//             단일 반환인 경우 - 결과가 정확히 1개이지 않으면 Exception 발생(try-catch 필요)
//             query1.getSingleResult();

            /* 파라미터 바인딩 */
//            em.createQuery("select m from Member2 m where m.username = :username", Member2.class)
//                    .setParameter("username", "member1");
//            em.createQuery("select m from Member2 m where m.username = ?1", Member2.class)
//                    .setParameter(1, "member1"); // 순서대로 작성해야 되기에 유지보수가 어렵기에 비추천

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
