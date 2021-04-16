
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.ApiResponseMessage;
import rsupport.rsupport.Dto.SearchDto;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = UrlConstants.MEMBERS)
    public ResponseEntity<ApiResponseMessage> GetMembers(SearchDto searchDto) {
        ApiResponseMessage message = new ApiResponseMessage();
        try {
            message.setStatus("Success");
            message.setMessage("");
            message.setData(memberService.search(searchDto));
        }catch (Exception e) {
            message.setStatus("Fail");
            message.setMessage("");
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(message, HttpStatus.OK);
    }

}
