package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Position;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.dto.MemberDto;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private EntityManager em;

    @Test
    public void 이름_중복_체크() {
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

    @Test
    public void 맴버_이름검색() {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);

        memberRepository.save(Member.builder().name("AKim").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().name("KimSu").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().name("Bae").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().name("Mark").team(team).position(Position.MEMBER).build());

        em.flush();
        em.clear();

        String seachWord = "kim";

        //when
        List<MemberDto> findMembers = memberService.search(seachWord);

        //then
        for (MemberDto member : findMembers) {
            assertFalse(member.getName().contains(seachWord));
        }
    }

    @Test
    public void 맴버_팀명검색() {
        //given
        Team team1 = Team.builder().name("웹개발1팀").build();
        Team team2 = Team.builder().name("총무팀").build();
        teamRepository.save(team1);
        teamRepository.save(team2);
        memberRepository.save(Member.builder().team(team1).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().team(team2).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().team(team2).position(Position.MEMBER).build());

        em.flush();
        em.clear();

        String searchWord = "총무";

        //when
        List<MemberDto> findMembers = memberService.search(searchWord);

        //then
        for (MemberDto member : findMembers) {
            assertTrue(member.getTeamName().contains(searchWord));
        }
    }

    @Test
    public void 맴버_내선번호검색() {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);
        memberRepository.save(Member.builder().number("1001").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().number("1002").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().number("1004").team(team).position(Position.MEMBER).build());

        em.flush();
        em.clear();

        String searchWord = "1004";

        //when
        List<MemberDto> findMembers = memberService.search(searchWord);

        //then
        for (MemberDto member : findMembers) {
            assertTrue(member.getNumber().contains(searchWord));
        }
    }

    @Test
    public void 맴버_전화번호검색() {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);
        memberRepository.save(Member.builder().phone("010-0000-0000").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().phone("010-1111-1111").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().phone("010-2222-2222").team(team).position(Position.MEMBER).build());
        memberRepository.save(Member.builder().phone("010-3333-3333").team(team).position(Position.MEMBER).build());

        String searchWord = "2222";

        //when
        List<MemberDto> findMembers = memberService.search(searchWord);

        //then
        for (MemberDto member : findMembers) {
            assertTrue(member.getPhone().contains(searchWord));
        }
    }
}
