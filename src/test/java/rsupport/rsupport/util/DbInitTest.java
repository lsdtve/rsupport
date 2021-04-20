
package rsupport.rsupport.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;
import rsupport.rsupport.service.MemberService;
import rsupport.rsupport.service.TeamService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DbInitTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamService teamService;
    @Autowired private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void DbInit() throws Exception {
        //given
        ClassPathResource resource = new ClassPathResource("data/member.csv");
        String line;

        Path path = Paths.get(resource.getURI());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line,",");

            if (st.countTokens()<6)
                continue;

            String id = st.nextToken().trim();
            String name = st.nextToken().trim();
            String number = st.nextToken().trim();
            String phone = st.nextToken().trim();
            String teamName = st.nextToken().trim();
            String grade = st.nextToken().trim();
            String position = st.hasMoreTokens() ? st.nextToken().trim() : "팀원";

            if (teamName.isEmpty())
                continue;

            Team team = teamRepository.findByName(teamName).orElseGet(() -> {
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

            //when
            em.flush();
            em.clear();

            Member findMember = memberRepository.findById(saveMember.getId()).get();

            //then
            Assert.assertEquals(saveMember.getName(), findMember.getName());
            Assert.assertEquals(saveMember.getPhone(), findMember.getPhone());
            Assert.assertEquals(saveMember.getNumber(), findMember.getNumber());
            Assert.assertEquals(saveMember.getGrade(), findMember.getGrade());
            Assert.assertEquals(saveMember.getPosition(), findMember.getPosition());
            Assert.assertEquals(saveMember.getTeam().getName(), findMember.getTeam().getName());
        }
    }
}
