
package rsupport.rsupport.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;
import rsupport.rsupport.service.MemberService;
import rsupport.rsupport.service.TeamService;

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
    private final TeamService teamService;

    public void dbInit() {
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
                memberService.save(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
