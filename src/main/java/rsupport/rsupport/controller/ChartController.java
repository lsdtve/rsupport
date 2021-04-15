
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.ChartResponse;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.service.MemberService;
import rsupport.rsupport.service.TeamService;

@RestController
@RequiredArgsConstructor
public class ChartController {

    private final MemberService memberService;
    private final TeamService teamService;

    @GetMapping(value = UrlConstants.CHART)
    public ChartResponse getChart() {
        ChartResponse response = new ChartResponse();
        response.setData(teamService.findChart());
        return response;
    }
}
