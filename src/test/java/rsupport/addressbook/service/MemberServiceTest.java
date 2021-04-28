
package rsupport.addressbook.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.dto.MemberDto;
import rsupport.addressbook.repository.MemberRepository;
import rsupport.addressbook.repository.TeamRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;
    @Autowired private EntityManager em;

    @Test
    public void 이름_중복_체크() throws Exception {
        //given
        String name = "홍길동";
        String[] nameAddList = {"", "(B)", "(C)", "(D)","(E)"};

        //when
        for (String s : nameAddList) {
            Member member = memberService.save(MemberCreateForm.builder().name(name).build());

            //then
            Assert.assertEquals(member.getName(), name + s);
            Assert.assertEquals(member.getOriginalName(), name);
        }
    }

    @Test
    public void 맴버_이름검색() throws Exception {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);

        memberRepository.save(Member.builder().name("AKim").team(team).build());
        memberRepository.save(Member.builder().name("KimSu").team(team).build());
        memberRepository.save(Member.builder().name("Bae").team(team).build());
        memberRepository.save(Member.builder().name("Mark").team(team).build());

        em.flush();
        em.clear();

        String seachWord = "kim";

        //when
        List<MemberDto> findMembers = memberService.search(seachWord);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertTrue(!member.getName().contains(seachWord));
        }
    }

    @Test
    public void 맴버_팀명검색() throws Exception {
        //given
        Team team1 = Team.builder().name("웹개발1팀").build();
        Team team2 = Team.builder().name("총무팀").build();
        teamRepository.save(team1);
        teamRepository.save(team2);
        memberRepository.save(Member.builder().team(team1).build());
        memberRepository.save(Member.builder().team(team2).build());
        memberRepository.save(Member.builder().team(team2).build());

        em.flush();
        em.clear();

        Team findTeam = teamRepository.findByName("총무팀").get();
        String searchWord = "총무";

        //when
        List<MemberDto> findMembers = memberService.search(searchWord);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertTrue(member.getTeamName().contains(searchWord));
        }
    }

    @Test
    public void 맴버_내선번호검색() throws Exception {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);
        memberRepository.save(Member.builder().number("1001").team(team).build());
        memberRepository.save(Member.builder().number("1002").team(team).build());
        memberRepository.save(Member.builder().number("1004").team(team).build());

        em.flush();
        em.clear();

        String searchWord = "1004";

        //when
        List<MemberDto> findMembers = memberService.search(searchWord);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertTrue(member.getNumber().contains(searchWord));
        }
    }

    @Test
    public void 맴버_전화번호검색() throws Exception {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);
        memberRepository.save(Member.builder().phone("010-0000-0000").team(team).build());
        memberRepository.save(Member.builder().phone("010-1111-1111").team(team).build());
        memberRepository.save(Member.builder().phone("010-2222-2222").team(team).build());
        memberRepository.save(Member.builder().phone("010-3333-3333").team(team).build());

        String searchWord = "2222";

        //when
        List<MemberDto> findMembers = memberService.search(searchWord);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertTrue(member.getPhone().contains(searchWord));
        }
    }
}
