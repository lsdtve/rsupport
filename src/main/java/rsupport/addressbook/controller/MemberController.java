
package rsupport.addressbook.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = UrlConstants.MEMBERS)
    public ResponseEntity<Map<String, Object>> getMembers(
            @RequestParam(value = "searchWord", required = false) String searchWord) {
        Map<String, Object> data = new HashMap<>();

        data.put("members", memberService.search(searchWord));

        return ResponseEntity.ok().body(data);
    }
}
