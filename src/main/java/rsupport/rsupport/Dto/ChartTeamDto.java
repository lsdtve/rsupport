
package rsupport.rsupport.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rsupport.rsupport.domain.Team;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ChartTeamDto {
    private String teamName;
    private List<ChartMemberDto> members;

    public ChartTeamDto(Team team) {
        this.teamName = team.getName();
        this.members = team.getMembers().stream()
                .map(ChartMemberDto::new)
                .collect(Collectors.toList());
    }
}
