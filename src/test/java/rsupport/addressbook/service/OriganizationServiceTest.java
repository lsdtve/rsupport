package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.MemberCreateForm;
import rsupport.addressbook.dto.OrganizationChartMemberDto;
import rsupport.addressbook.dto.OrganizationChartTeamDto;
import rsupport.addressbook.repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OriganizationServiceTest {

	@Autowired private MemberService memberService;
	@Autowired private OriganizationChartService origanizationService;
	@Autowired private TeamRepository teamRepository;
	@Autowired private EntityManager em;

	@Test
	void 조직도_팀_이름정렬() {
		//given
		String[] teamNameList = {"웹개발2팀", "웹개발1팀", "웹개발4팀", "웹개발3팀","인사팀","총무팀","기획팀"};

		for (String teamName : teamNameList) {
			teamRepository.save(Team.builder().name(teamName).build());
		}

		Arrays.sort(teamNameList);

		em.flush();
		em.clear();

		//when
		List<OrganizationChartTeamDto> teamList = origanizationService.findOrganizationChart();

		//then
		for (int i = 0; i < teamNameList.length ; i++) {
			assertEquals(teamNameList[i], teamList.get(i).getTeamName());
		}
	}

	@Test
	void 조직도_팀장_상위체크() {
		//given
		memberService.save(MemberCreateForm.builder().name("사").teamName("웹개발1팀").position("팀원").build());
		memberService.save(MemberCreateForm.builder().name("가").teamName("웹개발1팀").position("팀원").build());
		memberService.save(MemberCreateForm.builder().name("나").teamName("웹개발1팀").position("팀장").build());
		memberService.save(MemberCreateForm.builder().name("라").teamName("웹개발1팀").position("팀원").build());

		em.flush();
		em.clear();

		//when
		List<OrganizationChartTeamDto> findChart = origanizationService.findOrganizationChart();
		OrganizationChartMemberDto firstMember = findChart.get(0).getMembers().get(0);

		//then
		assertEquals("팀장", firstMember.getPosition());
	}

	@Test
	void 조직도_팀원_이름정렬() {
		//given
		String[] memberNameList = {"배용균", "홍길동", "이순신", "장도연", "강호동", "유재석"};

		for (String name : memberNameList) {
			memberService.save(MemberCreateForm.builder()
				.name(name)
				.position("팀원")
				.build());
		}

		Arrays.sort(memberNameList);

		em.flush();
		em.clear();

		//when
		List<OrganizationChartTeamDto> findChart = origanizationService.findOrganizationChart();
		List<OrganizationChartMemberDto> members = findChart.get(0).getMembers();

		//then
		for (int i = 0; i < memberNameList.length; i++) {
			assertEquals(memberNameList[i], members.get(i).getName());
		}
	}
}