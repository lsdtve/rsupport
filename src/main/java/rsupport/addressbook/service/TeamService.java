package rsupport.addressbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rsupport.addressbook.domain.Team;
import rsupport.addressbook.dto.OrganizationChartTeamDto;
import rsupport.addressbook.dto.TeamCreateForm;
import rsupport.addressbook.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team save(TeamCreateForm form) {
        Team team = Team.builder()
            .name(form.getName())
            .build();
        return teamRepository.save(team);
    }

    public List<OrganizationChartTeamDto> findOrganizationChart() {

        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"members.position"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"members.name"));

        List<Team> teamList = teamRepository.findAll(Sort.by(orders));

        return teamList.stream()
                .map(OrganizationChartTeamDto::new)
                .collect(Collectors.toList());
    }
}