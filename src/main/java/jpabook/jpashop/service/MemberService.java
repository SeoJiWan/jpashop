package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA 모든 데이터 변경이나 로직들은 트랜잭션 안에서 실행 -> LAZY 로딩 실행됨
@RequiredArgsConstructor // final 이 붙은 필드에 있는 것들만 생성자 인젝션으로 생성. (가장 좋은 방법)
public class MemberService {

//    @Autowired
//    private MemberRepository memberRepository; // 필드 인젝션 --> 바꿀 수 없다는 단점.(테스트할 경우)

    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) { // 생성자 인젝션
//        this.memberRepository = memberRepository;
//    }


    // 회원가입
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member);// 중복회원 검증
        memberRepository.save(member);


        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
