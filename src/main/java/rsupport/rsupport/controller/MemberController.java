
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.MemberDto;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.service.MemberService;
import rsupport.rsupport.service.TeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;

    @GetMapping(value = UrlConstants.MEMBERS)
    public List<MemberDto> GetMembers(SearchDto searchDto) {
        System.out.println("searchDto.getName() = " + searchDto.getNumber());
        return memberService.search(searchDto);
    }

}
