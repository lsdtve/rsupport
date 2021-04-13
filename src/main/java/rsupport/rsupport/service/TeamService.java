package rsupport.rsupport.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.TeamDto;
import rsupport.rsupport.Dto.TeamCreateForm;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public Team save(TeamCreateForm form) {
        Team team = Team.builder()
            .name(form.getName())
            .build();
        return teamRepository.save(team);
    }

    public List<TeamDto> findChart() {
        List<TeamDto> result = new ArrayList<>();
        List<Team> teamList = teamRepository.findAll().stream()
                .sorted(Comparator.comparing(Team::getName))
                .collect(Collectors.toList());
        teamList.forEach(team -> {
            TeamDto teamDto = new TeamDto();
            teamDto.setName(team.getName());
            teamDto.setMembers(sortMembers(team.getMembers()));
            result.add(teamDto);
        });
        return result;
    }

    public List<MemberDto> sortMembers(List<Member> members) {
        return members.stream()
                .sorted(Comparator.comparing(Member::getName))
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }
}