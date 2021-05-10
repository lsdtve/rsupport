package rsupport.addressbook.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import rsupport.addressbook.dto.OrganizationChartTeamDto;

@Setter
@Getter
public class OrganizationChartResponse {
	List<OrganizationChartTeamDto> teamList;
}
