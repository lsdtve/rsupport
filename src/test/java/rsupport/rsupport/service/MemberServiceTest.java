
package rsupport.rsupport.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void 이름_중복_체크() throws Exception {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);

        String name = "홍길동";
        String[] nameAddList = {"", "(B)", "(C)", "(D)","(E)"};

        //when
        for (int i = 0; i < nameAddList.length ; i++) {
            Member member = memberService.save(MemberCreateForm.builder().name(name).build());

            //then
            Assert.assertEquals(member.getName(), name + nameAddList[i]);
            Assert.assertEquals(member.getOriginalName(), name);
        }
    }

    @Test
    public void 맴버_이름검색() throws Exception {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);

        memberRepository.save(Member.builder().name("Kim").team(team).build());
        memberRepository.save(Member.builder().name("Kim").team(team).build());
        memberRepository.save(Member.builder().name("Bae").team(team).build());
        memberRepository.save(Member.builder().name("Mark").team(team).build());

        em.flush();
        em.clear();

        SearchDto searchDto = SearchDto.builder().name("kim").build();

        //when
        List<MemberDto> findMembers = memberService.search(searchDto);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertEquals(searchDto.getName(),member.getName());
        }
        Assert.assertEquals(memberRepository.countByOriginalName(searchDto.getName()),findMembers.size());
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

        SearchDto searchDto = SearchDto.builder().teamName("총무팀").build();
        Team findTeam = teamRepository.findByName(searchDto.getTeamName()).get();

        //when
        List<MemberDto> findMembers = memberService.search(searchDto);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertEquals(searchDto.getTeamName(),member.getTeamName());
        }
        Assert.assertEquals(findTeam.getMembers().size(), findMembers.size());
    }

    @Test
    public void 맴버_내선번호검색() throws Exception {
        //given
        Team team = Team.builder().name("웹개발1팀").build();
        teamRepository.save(team);
        memberRepository.save(Member.builder().number(1001).team(team).build());
        memberRepository.save(Member.builder().number(1002).team(team).build());
        memberRepository.save(Member.builder().number(1004).team(team).build());

        em.flush();
        em.clear();

        SearchDto searchDto = SearchDto.builder().number(1004).build();

        //when
        List<MemberDto> findMembers = memberService.search(searchDto);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertEquals(searchDto.getNumber(), member.getNumber());
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

        SearchDto searchDto = SearchDto.builder().phone("010-2222-2222").build();

        //when
        List<MemberDto> findMembers = memberService.search(searchDto);

        //then
        for (MemberDto member : findMembers) {
            Assert.assertEquals(searchDto.getPhone(), member.getPhone());
        }
    }
}