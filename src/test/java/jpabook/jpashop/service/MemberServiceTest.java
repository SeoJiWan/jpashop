package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest // @RunWith, @SpringBootTest ~ 스프링이랑 인티그레이션해서 스프링부트를 올려서 테스트 가능., junit5 는 필요 X
@Transactional // 테스트 후 rollback --> 테스트라서 db 에 데이터 안 남게 하기 위함.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
//    @Rollback(value = false) // insert 쿼리문 보고싶을 때..
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member); // em.persist() --> db 에 insert 문 안나감. commit 을 하지 않음

        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class) // expected = IllegalStateException.class --> 예외가 IllegalStateException 이면 테스트 통과
    public void 중복회원_학인() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외 발생 !

        // then
        fail("예외가 발생해야 한다."); // 여기 오면 안된다는 뜻
    }

}