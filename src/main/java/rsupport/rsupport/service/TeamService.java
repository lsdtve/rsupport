
package rsupport.rsupport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rsupport.rsupport.Dto.ChartMemberDto;
import rsupport.rsupport.Dto.ChartTeamDto;
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Comparator;
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

        List<Team> teamList = teamRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        teamList.forEach(team -> {
            ChartTeamDto teamDto = new ChartTeamDto();
            teamDto.setTeamName(team.getName());
            teamDto.setMembers(sortMembers(team.getMembers()));
            result.add(teamDto);
        });
        return result;
    }

    public List<ChartMemberDto> sortMembers(List<Member> members) {
        List<ChartMemberDto> memberList = members.stream()
                .sorted(Comparator.comparing(Member::getName))
                .map(ChartMemberDto::new)
                .collect(Collectors.toList());

        for (int i = 0; i < members.size() ; i++) {
            if (memberList.get(i).getPosition().equals("팀장")) {
                memberList.add(0,memberList.remove(i));
                break;
            }
        }

        return memberList;
    }
}