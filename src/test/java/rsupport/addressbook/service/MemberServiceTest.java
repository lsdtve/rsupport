package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Member;
import rsupport.addressbook.domain.Position;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.OrganizationChartMemberDto;
import rsupport.addressbook.dto.OrganizationChartTeamDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private EntityManager em;

    @Test // todo display 메소드로 만들기
    @DisplayName("이름 중복체크 확인")
    void duplicateName() {
        //given
        String name = "홍길동";
        String[] nameAddList = {"", "(B)", "(C)", "(D)","(E)"};

        //when
        for (String s : nameAddList) {
            String duplicateName = memberService.duplicateName(name);
            Member member = memberService.save(Member.builder()
                .name(duplicateName)
                .position(Position.MEMBER).build());

            //then
            assertEquals(member.getName(), name + s);
        }
    }

    @Test
    @DisplayName("팀 변경")
    void changeTeam() {
        //given
        Team team1 = Team.builder().name("웹개발1팀").build();
        Team team2 = Team.builder().name("웹개발2팀").build();
        Member member = memberService.save(Member.builder().name("홍길동").team(team1).position(Position.MEMBER).build());

        em.flush();
        em.clear();

        assertEquals(member.getTeam().getName(), team1.getName());

        //when
        member.changeTeam(team2);
        em.flush();

        //then
        assertAll(
            () -> assertEquals(member.getTeam().getName(), team2.getName()),
            () -> assertEquals(team2.getMembers().get(0), member)
        );
    }

    @Test
    @DisplayName("조직도 팀 이름 정렬")
    void organizationChartSortTeam() {
        //given
        String[] teamNameList = {"웹개발2팀", "웹개발1팀", "웹개발4팀", "웹개발3팀","인사팀","총무팀","기획팀"};

        for (String teamName : teamNameList) {
            memberService.save(Member.builder().name("홍길동").team(Team.builder().name(teamName).build()).position(Position.MEMBER).build());
        }

        Arrays.sort(teamNameList);

        em.flush();
        em.clear();

        //when
        List<OrganizationChartTeamDto> teamList = memberService.getOrganizationChart(null);

        //then
        for (int i = 0; i < teamNameList.length ; i++) {
            assertEquals(teamNameList[i], teamList.get(i).getTeamName());
        }
    }

    @Test
    @DisplayName("조직도 팀장 상위")
    void organizationChartFirstLeader() {
        //given
        Team team = Team.builder().name("웹개발1팀").build();

        memberService.save(Member.builder().name("사").team(team).position(Position.MEMBER).build());
        memberService.save(Member.builder().name("가").team(team).position(Position.MEMBER).build());
        memberService.save(Member.builder().name("다").team(team).position(Position.LEADER).build());
        memberService.save(Member.builder().name("").team(team).position(Position.MEMBER).build());

        em.flush();
        em.clear();

        //when
        List<OrganizationChartTeamDto> findChart = memberService.getOrganizationChart(null);
        OrganizationChartMemberDto firstMember = findChart.get(0).getMembers().get(0);

        //then
        assertEquals("팀장", firstMember.getPosition());
    }

    @Test
    @DisplayName("조직도 팀원 이름 정렬")
    void organizationChartSortName() {
        //given
        String[] memberNameList = {"배용균", "홍길동", "이순신", "장도연", "강호동", "유재석"};
        Team team = Team.builder().name("웹개발1팀").build();

        for (String name : memberNameList) {
            memberService.save(Member.builder()
                .name(name)
                .position(Position.MEMBER)
                .team(team)
                .build());
        }

        Arrays.sort(memberNameList);

        em.flush();
        em.clear();

        //when
        List<OrganizationChartTeamDto> findChart = memberService.getOrganizationChart(null);
        List<OrganizationChartMemberDto> members = findChart.get(0).getMembers();

        //then
        for (int i = 0; i < memberNameList.length; i++) {
            assertEquals(memberNameList[i], members.get(i).getName());
        }
    }
}
