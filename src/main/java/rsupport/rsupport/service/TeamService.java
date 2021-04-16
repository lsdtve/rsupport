
package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rsupport.rsupport.Dto.ChartMemberDto;
import rsupport.rsupport.Dto.ChartTeamDto;
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public Team save(TeamCreateForm form) {
        Team team = Team.builder()
            .name(form.getName())
            .build();
        return teamRepository.save(team);
    }

    public List<ChartTeamDto> findChart() {
        List<ChartTeamDto> result = new ArrayList<>();
        Sort.Order Order;

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
        orders.add(new Sort.Order(Sort.Direction.DESC,"members.position"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"members.name"));

        List<Team> teamList = teamRepository.findAll(Sort.by(orders));

        teamList.forEach(team -> {
            ChartTeamDto teamDto = new ChartTeamDto();
            teamDto.setTeamName(team.getName());
            teamDto.setMembers(team.getMembers().stream()
                    .map(ChartMemberDto::new)
                    .collect(Collectors.toList())
            );
            result.add(teamDto);
        });
        return result;
    }
}