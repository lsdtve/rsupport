package rsupport.addressbook.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.addressbook.dto.OrganizationChartTeamDto;
import rsupport.addressbook.repository.OrganizationChartRepository;

@Service
@RequiredArgsConstructor
public class OrganizationChartService {

	private final OrganizationChartRepository origanizationChartRepository;

	@Transactional(readOnly = true)
	public List<OrganizationChartTeamDto> findOrganizationChart(String searchWord) {
		return origanizationChartRepository.findOrganizationChart(searchWord);
	}
}
