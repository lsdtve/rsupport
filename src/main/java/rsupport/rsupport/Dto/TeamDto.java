package rsupport.rsupport.Dto;

import lombok.Data;
import rsupport.rsupport.domain.Member;

import java.util.List;

@Data
public class TeamDto {
    private String name;
    private MemberDto leader;
    private List<MemberDto> members;
}
