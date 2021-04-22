
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.ApiResponseMessage;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = UrlConstants.MEMBERS)
    public ResponseEntity<ApiResponseMessage> GetMembers(
            @RequestParam(value = "searchWord", required = false) String searchWord) {

        ApiResponseMessage message = new ApiResponseMessage();

        Map<String, Object> data = new HashMap<>();
        data.put("members", memberService.search(searchWord));

        message.setStatus("200");
        message.setMessage("");
        message.setData(data);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
