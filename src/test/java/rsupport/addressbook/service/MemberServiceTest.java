package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private EntityManager em;

    @Test
    void 이름_중복_체크() {
        //given
        String name = "홍길동";
        String[] nameAddList = {"", "(B)", "(C)", "(D)","(E)"};

        //when
        for (String s : nameAddList) {
            Member member = memberService.save(MemberCreateForm.builder().name(name).build());

            //then
            assertEquals(member.getName(), name + s);
            assertEquals(member.getOriginalName(), name);
        }
    }
}
