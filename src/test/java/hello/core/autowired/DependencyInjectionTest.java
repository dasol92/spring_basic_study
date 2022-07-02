package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DependencyInjectionTest {

    @Test
    @DisplayName("[나만의 테스트 코드] 의존관계 주입 테스트")
    void autoInjectionTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        OrderServiceImpl orderServiceImpl = ac.getBean(OrderServiceImpl.class);
        MemberServiceImpl memberServiceImpl = ac.getBean(MemberServiceImpl.class);

        MemberRepository memberRepositoryFromOrderServiceImpl = orderServiceImpl.getMemberRepository();
        MemberRepository memberRepositoryFromMemberServiceImpl = memberServiceImpl.getMemberRepository();

//        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class); // AutoAppConfig 에는 memberRepository 이름의 스프링 빈이 없음
        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        MemoryMemberRepository memoryMemberRepository = ac.getBean("memoryMemberRepository", MemoryMemberRepository.class);

        System.out.println("memberRepositoryFromOrderServiceImpl = " + memberRepositoryFromOrderServiceImpl);
        System.out.println("memberRepositoryFromMemberServiceImpl = " + memberRepositoryFromMemberServiceImpl);
        System.out.println("memberRepository = " + memberRepository);
        System.out.println("memoryMemberRepository = " + memoryMemberRepository);

        Assertions.assertThat(memberRepositoryFromOrderServiceImpl).isSameAs(memberRepository);
        Assertions.assertThat(memberRepositoryFromMemberServiceImpl).isSameAs(memberRepository);
        Assertions.assertThat(memoryMemberRepository).isSameAs(memberRepository);
    }
}
