package rsupport.rsupport.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;
import rsupport.rsupport.service.MemberService;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Component
public class DbInit {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public void db_init() {
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
                
                MemberCreateForm member = new MemberCreateForm();

                member.setName(temp[1].trim());
                member.setNumber(Integer.parseInt(temp[2].trim()));
                member.setPhone(temp[3].trim());
                member.setTeam(team);
                member.setSpot(temp[5].trim());
                member.setPosition(temp.length==7 ? temp[6].trim(): "팀원");
                memberService.save(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
