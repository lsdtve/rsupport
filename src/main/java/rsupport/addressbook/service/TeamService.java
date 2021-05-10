package rsupport.addressbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Team findTeamOrElseSave(String teamName) {
        return teamRepository.findByName(teamName).orElseGet(() ->
                teamRepository.save(Team.builder().name(teamName).build())
            );
    }

    public void deleteAll() {
        teamRepository.deleteAll();
    }

    public long count() {
        return teamRepository.count();
    }
}