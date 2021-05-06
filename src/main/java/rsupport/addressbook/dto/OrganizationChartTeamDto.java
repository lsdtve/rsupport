package rsupport.addressbook.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rsupport.addressbook.domain.Member;

@Getter
@Setter
@NoArgsConstructor
public class OrganizationChartTeamDto {
    private String teamName;
    private List<OrganizationChartMemberDto> members;

    public OrganizationChartTeamDto(String teamName, List<Member> members) {
        this.teamName = teamName;
        this.members = members.stream()
            .map(OrganizationChartMemberDto::new)
            .collect(Collectors.toList());
    }
}
