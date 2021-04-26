
package rsupport.addressbook.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.Dto.OrganizationChartMemberDto;
import rsupport.addressbook.Dto.OrganizationChartTeamDto;
import rsupport.addressbook.Dto.MemberCreateForm;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private TeamService teamService;
    @Autowired private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void 조직도_팀_이름정렬() throws Exception {
        //given
        String[] teamNameList = {"웹개발2팀", "웹개발1팀", "웹개발4팀", "웹개발3팀","인사팀","총무팀","기획팀"};

        for (String teamName : teamNameList) {
            teamRepository.save(Team.builder().name(teamName).build());
        }

        Arrays.sort(teamNameList);

        em.flush();
        em.clear();

        //when
        List<OrganizationChartTeamDto> teamList = teamService.findOrganizationChart();

        //then
        for (int i = 0; i < teamNameList.length ; i++) {
            Assert.assertEquals(teamNameList[i], teamList.get(i).getTeamName());
        }

     }

    @Test
    public void 조직도_팀장_상위체크() throws Exception {
        //given
        memberService.save(MemberCreateForm.builder().name("사").teamName("웹개발1팀").position("팀원").build());
        memberService.save(MemberCreateForm.builder().name("가").teamName("웹개발1팀").position("팀원").build());
        memberService.save(MemberCreateForm.builder().name("나").teamName("웹개발1팀").position("팀장").build());
        memberService.save(MemberCreateForm.builder().name("라").teamName("웹개발1팀").position("팀원").build());

        em.flush();
        em.clear();

        //when
        List<OrganizationChartTeamDto> findChart = teamService.findOrganizationChart();
        OrganizationChartMemberDto firstMember = findChart.get(0).getMembers().get(0);

        //then
        Assert.assertEquals("팀장", firstMember.getPosition());
     }

     @Test
     public void 조직도_팀원_이름정렬() throws Exception {
         //given
         String[] memberNameList = {"배용균", "홍길동", "이순신", "장도연", "강호동", "유재석"};

         Team team = Team.builder().name("웹개발1팀").build();
         teamRepository.save(team);

         for (String name : memberNameList) {
             memberService.save(MemberCreateForm.builder()
                     .name(name)
//                     .teamName(team)
                     .position("팀원")
                     .build());
         }

         Arrays.sort(memberNameList);

         em.flush();
         em.clear();

         //when
         List<OrganizationChartTeamDto> findChart = teamService.findOrganizationChart();
         List<OrganizationChartMemberDto> members = findChart.get(0).getMembers();

         //then
         for (int i = 0; i < memberNameList.length; i++) {
             Assert.assertEquals(memberNameList[i], members.get(i).getName());
         }
      }

}