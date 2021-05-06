package rsupport.addressbook.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.OrganizationChartService;

@RestController
@RequiredArgsConstructor
public class OrganizationChartController {

    private final OrganizationChartService origanizationService;

    @GetMapping(value = UrlConstants.ORGANIZATION_CHART)
    public ResponseEntity<Map<String, Object>> getOrganizationChart(
        @RequestParam(value = "searchWord", required = false) String searchWord) {
        Map<String, Object> data = new HashMap<>();

        data.put("teamList", origanizationService.findOrganizationChart(searchWord));

        return ResponseEntity.ok().body(data);
    }
}
