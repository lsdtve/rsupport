package rsupport.rsupport.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.TeamDto;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;
import rsupport.rsupport.repository.MemberRepository;
import rsupport.rsupport.repository.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public List<TeamDto> findChart() {
        List<TeamDto> result = new ArrayList<>();
        List<Team> teamList = teamRepository.findAll();
        teamList.forEach(team -> {
            TeamDto teamDto = new TeamDto();
            teamDto.setName(team.getName());
            teamDto.setLeader(findLeader(team.getId()));
            teamDto.setMembers(findMembers(team.getId()));
            result.add(teamDto);
        });
        return result;
    }

    private List<MemberDto> findMembers(Long teamId) {
        List<MemberDto> members = new ArrayList<>();
        memberRepository.findByMembers(teamId).forEach(member -> {
            members.add(new MemberDto(member));
        });
        return members;
    }


    public MemberDto findLeader(Long teamId) {
        Optional<Member> leader = memberRepository.findByLeader(teamId);
        if (!leader.isPresent()){
            return null;
        }
        return new MemberDto(leader.get());
    }
}
