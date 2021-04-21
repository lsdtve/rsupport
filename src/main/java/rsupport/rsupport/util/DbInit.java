
package rsupport.rsupport.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import rsupport.rsupport.Dto.MemberCreateForm;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;
import rsupport.rsupport.service.MemberService;
import rsupport.rsupport.service.TeamService;

import java.io.IOException;
import java.nio.file.Files;
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
        Path path = Paths.get(resource.getURI());

        Files.lines(path)
            .map(str -> new StringTokenizer(str,","))
            .filter(st -> 6 <= st.countTokens())
            .forEach(st -> {
                String id = st.nextToken();
                String name = rtrim(st.nextToken());
                String number = rtrim(st.nextToken());
                String phone = rtrim(st.nextToken());
                String teamName = rtrim(st.nextToken());
                String grade = rtrim(st.nextToken());
                String position = st.hasMoreTokens() ? rtrim(st.nextToken()) : "팀원";

                if (teamName.isEmpty())
                    return;

                Team team = findTeam(teamName);
                saveMember(name, number, phone, grade, position, team);
            });
    }

    public Member saveMember(String name, String number, String phone, String grade, String position, Team team) {
        MemberCreateForm member = MemberCreateForm.builder()
                .name(name)
                .number(number)
                .phone(phone)
                .team(team)
                .grade(grade)
                .position(position)
                .build();
        return memberService.save(member);
    }

    public Team findTeam(String teamName) {
        return teamRepository.findByName(teamName).orElseGet(()-> {
            return Team.builder().name(teamName).build();
        });
    }

    public String rtrim(String value) {
        int len = value.length();
        int st = 0;
        char[] val = value.toCharArray();

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        return value.substring(st, len);
    }

}
