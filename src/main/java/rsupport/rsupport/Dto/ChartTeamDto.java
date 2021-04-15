
package rsupport.rsupport.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartTeamDto {
    private String teamName;
    private List<ChartMemberDto> members;
}
