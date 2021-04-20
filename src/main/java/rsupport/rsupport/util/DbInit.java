
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Component
public class DbInit {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;

    public void dbInit() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/member.csv");
        String line;

        Path path = Paths.get(resource.getURI());
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString())));

        while((line = br.readLine()) != null) {
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
    }

}
