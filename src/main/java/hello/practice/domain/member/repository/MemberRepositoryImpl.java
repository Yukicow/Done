package hello.practice.domain.member.repository;

import hello.practice.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final Map<Long, Member> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;


    public Member save(Member member){
        member.setId(++sequence);
        store.put(sequence, member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }


}
