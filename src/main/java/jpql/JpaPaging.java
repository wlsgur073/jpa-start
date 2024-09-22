package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.domain.Member2;

import java.util.List;

public class JpaPaging {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {

            for (int i = 0; i < 100; i++) {
                Member2 member = new Member2();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            // sql로는 그지같은 query를 작성해야 하지만 jpa에서는 api가 깔끔하다.
            List<Member2> resultList = em
                    .createQuery("select m from Member2  m order by m.age desc", Member2.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();


            for (Member2 m : resultList) {
                System.out.println("member name = " + m.getUsername() + " age : " + m.getAge());
            }


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
