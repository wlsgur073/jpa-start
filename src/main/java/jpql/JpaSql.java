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
