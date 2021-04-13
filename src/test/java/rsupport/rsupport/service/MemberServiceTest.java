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
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.Dto.TeamDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamService teamService;
    @Autowired private TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void DbInit() throws Exception {
        BufferedReader br = null;
        ClassPathResource resource = new ClassPathResource("data/member.csv");
        String line;
        try {
            Path path = Paths.get(resource.getURI());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), "UTF-8"));
            while((line = br.readLine()) != null) {
                String[] temp = line.split(",");

                Team team;
                if(!teamRepository.existsByName(temp[4].trim())) {
                    teamRepository.save(new Team(temp[4].trim()));
                }
                team = teamRepository.findByName(temp[4].trim());

                MemberCreateForm from = new MemberCreateForm();

                from.setName(temp[1].trim());
                from.setNumber(Integer.parseInt(temp[2].trim()));
                from.setPhone(temp[3].trim());
                from.setTeam(team);
                from.setSpot(temp[5].trim());
                from.setPosition(temp.length==7 ? temp[6].trim():null);
                Member saveMember = memberService.save(from);

                Assert.assertEquals(saveMember,memberRepository.findById(saveMember.getId()).get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void 이름_중복_체크() throws Exception {
        int memberSize = 10;
        String name = "홍길동";

        for (int i = 0; i < memberSize; i++) {
            MemberCreateForm form = new MemberCreateForm();
            form.setName(name);
            Member member =  memberService.save(form);

            String nameCheck = name + (i==0 ? "" : String.format("(%c)",i+65));

            Assert.assertEquals(member.getName(), nameCheck);
        }
     }
}