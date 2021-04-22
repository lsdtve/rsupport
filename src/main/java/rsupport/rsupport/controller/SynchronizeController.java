
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.Dto.ApiResponseMessage;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.util.DbInit;
import rsupport.rsupport.util.DbRemove;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SynchronizeController {

    private final DbInit dbInit;
    private final DbRemove dbRemove;

    @GetMapping(value = UrlConstants.SYNCHRONIZE)
    public ResponseEntity<ApiResponseMessage> synchronize() {
        ApiResponseMessage message = new ApiResponseMessage();

        Map<String, Object> data = new HashMap<>();

        message.setStatus("200");
        message.setMessage("주소록 동기화 성공");
        message.setData(data);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
