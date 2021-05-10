package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Team;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired private TeamService teamService;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("팀 검색 없다면 저장")
    void findTeamOrElseNewTeam() {
        //given
        Team team1 = teamService.save(Team.builder().name("웹개발1팀").build());

        em.flush();
        em.clear();

        //when
        Team findTeam1 = teamService.findTeamOrElseSave("웹개발1팀");
        Team findTeam2 = teamService.findTeamOrElseSave("웹개발2팀");

        //then

        assertEquals("웹개발1팀", findTeam1.getName());
        assertEquals("웹개발2팀", findTeam2.getName());
     }

}
