package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jpabook.japshop.domain.Member;

import java.util.List;

public class JpaSql {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            // 1. JPQL - 동적 쿼리 작성이 불편하다.
            List<Member> jpqlList = em.createQuery(
                    "select m from Member m where m.name like '%kim%'"
                    , Member.class
                    ).getResultList();

            // 2. Criteria - JPQL에 비해 동적 쿼리 작성이 편하지만, 가독성이 너무 좋지 않다.
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            // 루트 클래스(조회를 시작할 클래스)
            Root<Member> m = query.from(Member.class);
            // 쿼리 생성
            CriteriaQuery<Member> cq = query.select(m)
                    .where(
                            cb.equal(m.get("name"), "kim")
                    );
            List<Member> criteriaList = em.createQuery(cq).getResultList();

            /* 결론 QueryDSL을 사용하자. */

            /*
                executeUpdate()의 경우 auto flush 기능이 있기에 DB에는 자동 반영된다.
                그러나 영속성 컨텍스트에서는 반영되어 있지 않는다. 왜냐하면 아직 clear가 되어 있지 않기 때문이다.
                이러한 현상은 DB와 영속성 컨텍스트의 데이터 정합성에 맞지 않는 현상이 발생할 수 있다.
                그러기에 em.clear()를 해서 영속성 컨텍스트를 초기화 시킨 후, DB에서 다시 조회해서
                영속성 컨텍스트를 DB에 맞춰야 한다.


                https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.modifying-queries
                위에 Spring data JPA docs를 참고하면 "we do not automatically clear it"이라 작성되어 있다.
            */
//            em.createQuery("update Member m set m.name = 'kim'").executeUpdate();
//            em.clear();
//            em.find(Member.class, member1.getId());

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
