
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.ApiResponseMessage;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.service.TeamService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChartController {

    private final TeamService teamService;

    @GetMapping(value = UrlConstants.CHART)
    public ResponseEntity<ApiResponseMessage> getChart() {
        ApiResponseMessage message = new ApiResponseMessage();

        Map<String, Object> data = new HashMap<>();
        data.put("teamList", teamService.findChart());

        message.setStatus("200");
        message.setMessage("조직도");
        message.setData(data);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
