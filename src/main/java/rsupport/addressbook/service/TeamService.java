package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team findTeamOrElseNewTeam(String teamName) {
        return teamRepository.findByName(teamName).orElseGet(() ->
                Team.builder().name(teamName).build()
            );
    }
}