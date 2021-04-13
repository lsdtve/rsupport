package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.ChartResponse;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.service.MemberService;
import rsupport.rsupport.service.TeamService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;

    @GetMapping(value = "/chart")
    public ChartResponse GetChart() {
        ChartResponse response = new ChartResponse();
        response.setTeamChart(teamService.findChart());
        return response;
    }

    @GetMapping(value = "/members")
    public List<MemberDto> GetMembers(SearchDto searchDto) {
        memberService.Search(searchDto);
        return memberService.findAll();
    }

}
