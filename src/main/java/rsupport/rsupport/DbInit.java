package rsupport.rsupport;

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
public class DbInit implements CommandLineRunner {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Override
    public void run(String... args) throws Exception {
        db_init();
    }

    /**
     * - 직책은 팀장만 있음, 그 외 팀원
     * - 동명이인인 경우 이름 + (B, C, D)로 표기
     * - 순번, 이름, 내선번호, 휴대폰, 팀명, 직위, 직책
     *  1, 홍길동, 1000, 010-1234-5678, 웹개발1팀, 차장, 팀장
     *  2, 이순신, 2002, 010-2222-5678, 웹개발2팀, 과장,
     */
    public void db_init() {
        System.out.println("============== Db Init Start ================");
        BufferedReader br = null;
        ClassPathResource resource = new ClassPathResource("data/member.csv");
        String line;
        try {
            Path path = Paths.get(resource.getURI());
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), "UTF-8"));
            while((line = br.readLine()) != null) {
                String[] temp = line.split(", "); // 쉼표로 구분

                Team team;
                if(!teamRepository.existsByName(temp[4])) {
                    teamRepository.save(new Team(temp[4]));
                }
                team = teamRepository.findByName(temp[4]);
                
                MemberCreateForm member = new MemberCreateForm();

                member.setName(temp[1]);
                member.setNumber(Integer.parseInt(temp[2].trim()));
                member.setPhone(temp[3]);
                member.setTeam(team);
                member.setSpot(temp[5]);
                member.setPosition(temp.length==7 ? temp[6]:null);
                memberService.save(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("============== Db Init End ================");
    }

}
