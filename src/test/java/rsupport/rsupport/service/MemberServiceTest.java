package rsupport.rsupport.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamService teamService;
    @Autowired private TeamRepository teamRepository;

    @Test
    public void DbInit() throws Exception {
        BufferedReader br = null;
        ClassPathResource resource = new ClassPathResource("data/member.csv");
        String line;
        try {
            Path path = Paths.get(resource.getURI());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), "UTF-8"));
            while((line = br.readLine()) != null) {
                String[] temp = line.replace(" ","").split(",");

                String name = temp[1];
                int number = Integer.parseInt(temp[2]);
                String phone = temp[3];
                String teamName = temp[4];
                String grade = temp[5];
                String position = (temp.length==7 ? temp[6].trim(): "팀원");

                Team team = teamRepository.findByName(teamName).orElseGet(()-> {
                    TeamCreateForm teamForm = TeamCreateForm.builder()
                            .name(teamName)
                            .build();
                    return teamService.save(teamForm);
                });

                MemberCreateForm member = MemberCreateForm.builder()
                        .name(name)
                        .number(number)
                        .phone(phone)
                        .team(team)
                        .grade(grade)
                        .position(position)
                        .build();
                Member saveMember = memberService.save(member);
                Assert.assertEquals(saveMember,memberRepository.findById(saveMember.getId()).get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void 이름_중복_체크() throws Exception {

        String[] testName = new String[] {"","(B)","(C)","(D)","(E)"};
        String name = "홍길동";

        for (int i = 0; i < testName.length ; i++) {
            MemberCreateForm form = MemberCreateForm.builder()
                    .name(name)
                    .grade("차장")
                    .position("팀장")
                    .build();
            Member member =  memberService.save(form);

            Assert.assertEquals(member.getName(), name + testName[i]);
        }
     }

    @Test
    public void 맴버_이름검색() throws Exception {

        TeamCreateForm teamForm = TeamCreateForm.builder()
                .name("웹개발1팀")
                .build();
        Team team = teamService.save(teamForm);

        MemberCreateForm form = MemberCreateForm.builder()
                .name("홍길동")
                .team(team)
                .build();
        Member member = memberService.save(form);

        MemberCreateForm form1 = MemberCreateForm.builder()
                .name("나경한")
                .team(team)
                .build();
        memberService.save(form1);

        SearchDto searchDto = new SearchDto();
        searchDto.setName("홍길동");

        Assert.assertEquals(member.getName(), memberService.search(searchDto).get(0).getName());

    }

    @Test
    public void 맴버_팀명검색() throws Exception {

        TeamCreateForm teamForm = TeamCreateForm.builder()
                .name("웹개발1팀")
                .build();
        Team team = teamService.save(teamForm);

        TeamCreateForm teamForm1 = TeamCreateForm.builder()
                .name("웹개발2팀")
                .build();
        Team team1 = teamService.save(teamForm1);

        MemberCreateForm form = MemberCreateForm.builder()
                .name("홍길동")
                .team(team)
                .build();
        Member member = memberService.save(form);

        MemberCreateForm form1 = MemberCreateForm.builder()
                .name("나경한")
                .team(team1)
                .build();
        memberService.save(form1);

        SearchDto searchDto = new SearchDto();
        searchDto.setTeamName("웹개발1팀");

        Assert.assertEquals(member.getTeam().getName(), memberService.search(searchDto).get(0).getTeamName());
    }

    @Test
    public void 맴버_내선번호검색() throws Exception {

        TeamCreateForm teamForm = TeamCreateForm.builder()
                .name("웹개발1팀")
                .build();
        Team team = teamService.save(teamForm);

        MemberCreateForm form = MemberCreateForm.builder()
                .name("홍길동")
                .team(team)
                .number(1004)
                .build();
        Member member = memberService.save(form);

        MemberCreateForm form1 = MemberCreateForm.builder()
                .name("나경한")
                .team(team)
                .number(1001)
                .build();
        memberService.save(form1);

        SearchDto searchDto = new SearchDto();
        searchDto.setNumber(1004);

        List<MemberDto> searchMembers = memberService.search(searchDto);

        Assert.assertEquals(1, searchMembers.size());
        Assert.assertEquals(member.getNumber(), searchMembers.get(0).getNumber());
    }

    @Test
    public void 맴버_전화번호검색() throws Exception {

        TeamCreateForm teamForm = TeamCreateForm.builder()
                .name("웹개발1팀")
                .build();
        Team team = teamService.save(teamForm);

        MemberCreateForm form = MemberCreateForm.builder()
                .name("홍길동")
                .team(team)
                .phone("010-1234-1234")
                .build();
        Member member = memberService.save(form);

        MemberCreateForm form1 = MemberCreateForm.builder()
                .name("나경한")
                .team(team)
                .phone("010-0000-0000")
                .build();
        memberService.save(form1);

        SearchDto searchDto = new SearchDto();
        searchDto.setPhone("010-1234-1234");

        List<MemberDto> searchMembers = memberService.search(searchDto);

        Assert.assertEquals(1, searchMembers.size());
        Assert.assertEquals(member.getPhone(), memberService.search(searchDto).get(0).getPhone());
    }
}