package rsupport.addressbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.response.OrganizationChartResponse;
import rsupport.addressbook.service.MemberService;

@RestController
@RequiredArgsConstructor
public class OrganizationChartController {

    private final MemberService memberservice;

    @GetMapping(value = UrlConstants.ORGANIZATION_CHART)
    public ResponseEntity<OrganizationChartResponse> getOrganizationChart(
        @RequestParam(value = "searchWord", required = false) String searchWord) {
        OrganizationChartResponse response = new OrganizationChartResponse();

        response.setTeamList(memberservice.getOrganizationChart(searchWord));

        return ResponseEntity.ok().body(response);
    }
}
