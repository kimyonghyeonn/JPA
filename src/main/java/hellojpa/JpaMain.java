package hellojpa;

import org.hibernate.internal.build.AllowSysOut;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            //      영속성 컨텍스트: 엔티티를 영구 저장하는 환경
//        영속성 컨텍스트는 논리적인 개념 / 눈에 보이지 않으며 엔티티 매니저를 통해 영속성 컨텍스트에 접근한다

//        엔티티의 생명주기: 비영속 / 영속 / 준영속 / 삭제

//        1. 비영속: 객체를 생성하고 영속 컨텍스트에 안 넣은 상태
//            Member member = new Member();
//            member.setId(180L);
//            member.setName("User2");

//        2. 영속: 객체를 생성하고 영속 컨텍스트 내부에 넣은 상태 => persist메소드로 인해 DB에 저장되는것이 아님. 출력문을 통해 DB에 저장되는 부분이 어디인지 확인한다
//            System.out.println("persist를 하기 이전의 출력문입니다.");
//            em.persist(member); //엔티티를 영속성 컨텍스트에 넣어줌 (정확히는 1차 캐시에 저장)
//            System.out.println("persist를 실행한 이후의 출력문입니다.");


//        3. 준영속: 영속성 컨텍스트에서 분리하는 작업
//            em.detach(member);

//        4. 삭제: 객체를 삭제한 상태(삭제)
//            em.remove(member);


//            영속성 컨텍스트를 사용해서 얻는 이점
//            1차 캐시 / 동일성 보장 / 트랜잭션을 지원하는 쓰기 지연 / 변경 감지 / 지연 로딩


//            1차캐시에서 조회, 실행을 하면 select 쿼리가 나오지 않고 아래의 두 출력문이 정상작동되는것을 볼 수 있다.
//            이것은 위에서 persist로 1차 캐시에 member을 저장했기 때문에 1차캐시로부터 해당 값을 가져오므로 select 쿼리는 실행되지 않는다
//            만약 1차캐시에서 찾을수 없다면 DB로 가서 select쿼리를 날려서 해당 엔티티를 1차캐시에 저장하게 된다

//            아래 코드는 DB로부터 @Id가 180L인 엔티티를 가져와 1차 캐시에 넣는다(=쿼리가 실행된다)
            Member findMember1 = em.find(Member.class, 180L);
//            위에서 1차 캐시에 적재되어있기 때문에 쿼리를 실행하지 않고도 가져올수 있다.
            Member findMember2 = em.find(Member.class, 180L);
            System.out.println("1차 캐시에서 조회한 findMember.Id:"+findMember1.getId());
            System.out.println("1차 캐시에서 조회한 findMember.Name:"+findMember1.getName());




//            영속 엔티티의 동일성 보장
//            System.out.println("result = "+(findMember1 == findMember2)); //true값이 출력된다




//            트랜잭션을 지원하는 쓰기 지연
//            영속성 컨텍스트에는 1차 캐시 말고도 쓰기 지연 SQL저장소에 쿼리가 쌓이게 된다. 쌓인 쿼리는 커밋을 하는 시점에 실행된다
//            Member member1 = new Member(200L,"userE");
//            Member member2 = new Member(210L,"userF");
//
//            em.persist(member1);
//            em.persist(member2);
//          아래 출력문은 쓰기 지연 SQL저장소에 쌓인 쿼리가 commit에서 실행되는것을 보여주기 위해 작성됨
//            System.out.println("------------------------------------------------------------------------");






//            엔티티 수정(변경 감지)
//            1차 캐시 내부에는 PK(@Id), 엔티티, 스냅샷이 존재한다. 스냅샷은 값을 읽어온 최초 시점의 상태값을 넣어준 것이다.
//            커밋되는 시점에 1차 캐시에 적재되어있는 스냅샷들이 일치하는지 비교 => 바뀐것이 있다면 update쿼리를 쓰기 지연 SQL저장소에 만들어주고 DB에 update쿼리를 실행시킨다
            Member member = em.find(Member.class, 180L);
            member.setName("UserD"); //값만 바꿔도 update 쿼리가 실행된다.








        tx.commit();
//        트랜잭션을 커밋할때 쿼리가 날라간다. 이때 쿼리가 콘솔창에 찍히게 되는것이다.
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

















    }
}
