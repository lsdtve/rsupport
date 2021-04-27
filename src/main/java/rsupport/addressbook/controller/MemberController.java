
package rsupport.addressbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.dto.ApiResponseMessage;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = UrlConstants.MEMBERS)
    public ResponseEntity<ApiResponseMessage> getMembers(
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
