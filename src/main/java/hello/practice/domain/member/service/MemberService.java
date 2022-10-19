package hello.practice.domain.member.service;

import hello.practice.domain.member.Member;
import hello.practice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;


    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    public Member findById(Long id){
        return memberRepository.findById(id);}

    public Member login(String loginId, String password){
        return memberRepository.findAll().stream()
                .filter(m -> m.getPassword().equals(password) && m.getLoginId().equals(loginId))
                .findFirst()
                .orElse(null);
    }

}
