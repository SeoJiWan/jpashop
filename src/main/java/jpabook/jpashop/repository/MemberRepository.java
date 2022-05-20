package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // --> ComponentScan 에 의해 자동으로 스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext // 스프링에서 EntityManager 를 꺼내서 필드에 주입 --> @RequiredArgsConstructor 으로 대체
    private final EntityManager em; // 스프링이 em 을 만들어서 인젝션 해줌


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) { // 단 건 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // ( JPQL, 반환타입 )
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
