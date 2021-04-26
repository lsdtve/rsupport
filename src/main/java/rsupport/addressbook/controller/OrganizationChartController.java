
package rsupport.addressbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.Dto.ApiResponseMessage;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.TeamService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrganizationChartController {

    private final TeamService teamService;

    @GetMapping(value = UrlConstants.ORGANIZATION_CHART)
    public ResponseEntity<ApiResponseMessage> getOrganizationChart() {
        ApiResponseMessage message = new ApiResponseMessage();

        Map<String, Object> data = new HashMap<>();
        data.put("teamList", teamService.findOrganizationChart());

        message.setStatus("200");
        message.setMessage("조직도");
        message.setData(data);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
