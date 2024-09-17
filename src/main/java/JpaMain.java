import hellojpa.MappingMember;
import hellojpa.MappingTeam;
import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        // persistence.xml에 작성된 persistence-unit의 name을 읽어옴
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try {
            // 저장
            MappingTeam team = new MappingTeam();
            team.setName("TeamA");
            em.persist(team);

            MappingMember member = new MappingMember();
            member.setName("member1");
            member.changeTeam(team);
            em.persist(member);

            /*
            * 여기서 add member을 하게 되면 영속성 컨텍스트 1차 캐시에 존재함으로 select문을 호출하지 않고 가져온다.
            * member에서는 team을 세팅하고 team에서는 member을 세팅하지 않으면 team을 find할 때 memeber를 찾을 수 없게 된다.
            * 따라서, 양방향 연관관계를 맺을때는 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정해줘야 한다.
            * */
//            team.getMembers().add(member);

            /*
            * flush, clear를 하면 밑에 findTeam을 할 때, DB에서 영속성 컨텍스트로 가져오게 됨으로
            * select문을 호출한 뒤 getMembers()을 이행한다.
            * */
//            em.flush();
//            em.clear();

            MappingTeam findTeam = em.find(MappingTeam.class, team.getId()); // 1차 캐시에 존재함
            List<MappingMember> members = findTeam.getMembers();

            System.out.println("===========");
            for (MappingMember m: members) {
                System.out.println("member = " + m.getName());
            }
            System.out.println("===========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
