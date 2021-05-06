package rsupport.addressbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.TeamRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class TeamServiceTest {

    @Autowired private TeamRepository teamRepository;
    @Autowired private TeamService teamService;
    @Autowired private EntityManager em;

    @Test
    void findTeamOrElseNewTeam() {
        //given
        Team team1 = teamRepository.save(Team.builder().name("웹개발1팀").build());

        em.flush();
        em.clear();

        //when
        Team findTeam1 = teamService.findTeamOrElseNewTeam("웹개발1팀");
        Team findTeam2 = teamService.findTeamOrElseNewTeam("웹개발2팀");

        //then
        assertEquals("웹개발1팀", findTeam1.getName());
        assertEquals("웹개발2팀", findTeam2.getName());
     }

}
