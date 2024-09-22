import hellojpa.MappingMember;
import hellojpa.MappingTeam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaProxy {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            // team을 지연 로딩(LAZY)로 바꿔둔 상태
            MappingTeam team = new MappingTeam();
            team.setName("teamA");
            em.persist(team);

            MappingMember member = new MappingMember();
            member.setName("member1");
            member.changeTeam(team);
            em.persist(member);

//            em.flush();
//            em.clear();

            MappingMember findMember = em.find(MappingMember.class, member.getId());

            // em.find에서 member 조회를 위한 select query를 실행하지만 team은 proxy 객체를 가져온다.
            System.out.println("findMember = " + findMember.getTeam().getClass());

            // proxy에 있는 내부 method를 사용하는 순간, 비로소 select query가 실행된다.
            System.out.println("=============================");
            System.out.println("findMember.getTeam().getName() = " + findMember.getTeam().getName());
            System.out.println("=============================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
