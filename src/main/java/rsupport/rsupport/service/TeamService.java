
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

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
        orders.add(new Sort.Order(Sort.Direction.DESC,"members.position"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"members.name"));

        List<Team> teamList = teamRepository.findAll(Sort.by(orders));

        return teamList.stream()
                .map(ChartTeamDto::new)
                .collect(Collectors.toList());
    }
}