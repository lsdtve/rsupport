package rsupport.addressbook.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rsupport.addressbook.domain.Team;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationChartTeamDto {
    private String teamName;
    private List<OrganizationChartMemberDto> members;

    public OrganizationChartTeamDto(Team team) {
        this.teamName = team.getName();
        this.members = team.getMembers().stream()
                .map(OrganizationChartMemberDto::new)
                .collect(Collectors.toList());
    }
}
