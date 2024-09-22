package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpql.domain.Member2;
import jpql.domain.Team2;

import java.util.List;

public class JpqlJoin {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {

            Team2 teamA = new Team2();
            teamA.setName("팀A");
            em.persist(teamA);

            Team2 teamB = new Team2();
            teamB.setName("팀A");
            em.persist(teamB);

            Member2 member1 = new Member2();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member2 member2 = new Member2();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member2 member3 = new Member2();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

//            String query = "select m from Member2 m";
            String query1 = "select m from Member2 m join fetch m.team"; // 즉시로딩처럼
            String query2 = "select t from Team2 t join fetch t.member2s";
            List<Member2> resultList = em.createQuery(query1, Member2.class).getResultList();
            List<Team2> resultList2 = em.createQuery(query2, Team2.class).getResultList();

            /*for (Member2 member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차 캐시)
                // 회원3, 팀B(SQL)

                // 회원 100명 ?... -> N + 1 // 이것에 대한 해결 방안은 fetch join을 하는 방법이다.
                // 지연로딩이 되지 않는다. 즉시 로딩처럼 동작한다. 해당 부분만 fetch를 명시하여 즉시 로딩하는 것이다.
            }*/

            /*
            * fetch join 주의사항
            * - 1:N 둘 이상 컬렉션은 패치 조인을 할 수 없다.
            * - paging 처리하면 안됨. 데이터 범위가 커지기에 memory WARN 발생함.
            * */



            System.out.println("resultList2.size() = " + resultList2.size());

            for (Team2 team : resultList2) {
                System.out.println("member = " + team.getName() + " | " + team .getMember2s().size());
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
