package hello.practice.web;

import hello.practice.domain.item.DeliveryCode;
import hello.practice.domain.item.Item;
import hello.practice.domain.item.repository.ItemRepository;
import hello.practice.domain.member.Member;
import hello.practice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        itemRepository.save(new Item("ItemA", "test",10000, 10, DeliveryCode.QUICK, true));
        itemRepository.save(new Item("ItemB", "test",20000, 20, DeliveryCode.NORMAL, false));

        memberRepository.save(new Member("테스트 계정", "test", "test"));
        memberRepository.save(new Member("테스트 계정2", "test2", "test2"));

    }
}
