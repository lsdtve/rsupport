package rsupport.addressbook.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.OriganizationChartService;

@RestController
@RequiredArgsConstructor
public class OrganizationChartController {

    private final OriganizationChartService origanizationService;

    @GetMapping(value = UrlConstants.ORGANIZATION_CHART)
    public ResponseEntity<Map<String, Object>> getOrganizationChart() {
        Map<String, Object> data = new HashMap<>();

        data.put("teamList", origanizationService.findOrganizationChart());

        return ResponseEntity.ok().body(data);
    }
}
