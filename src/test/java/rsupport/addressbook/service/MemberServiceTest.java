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
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private TeamRepository teamRepository;
    @Autowired private EntityManager em;

    @Test
    void 이름_중복_체크() {
        //given
        String name = "홍길동";
        String[] nameAddList = {"", "(B)", "(C)", "(D)","(E)"};

        //when
        for (String s : nameAddList) {
            String duplicateName = memberService.duplicateName(name);
            Member member = memberService.save(Member.builder().originalName(name).name(duplicateName).build());

            //then
            assertEquals(member.getName(), name + s);
            assertEquals(member.getOriginalName(), name);
        }
    }

    @Test
    void 팀_변경() {
        //given
        Team team1 = teamRepository.save(Team.builder().name("웹개발1팀").build());
        Team team2 = teamRepository.save(Team.builder().name("웹개발2팀").build());
        Member member = memberService.save(Member.builder().name("홍길동").originalName("홍킬동").build());
        member.changeTeam(team1);

        //when
        member.changeTeam(team2);

        //then
        assertEquals(member.getTeam().getName(), team2.getName());
        assertEquals(team2.getMembers().get(0), member);
     }
}
