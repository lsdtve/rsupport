package rsupport.rsupport.Dto;

import lombok.Data;
import rsupport.rsupport.domain.Member;
import rsupport.rsupport.domain.Team;

import java.util.List;

@Data
public class TeamDto {
    private String name;
    private List<MemberDto> members;

    public TeamDto(Team team){
        this.name = team.getName();
    }

    public TeamDto() {

    }
}
