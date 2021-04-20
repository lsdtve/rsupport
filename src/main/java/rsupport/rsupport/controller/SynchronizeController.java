
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

@RestController
@RequiredArgsConstructor
public class SynchronizeController {

    private final DbInit dbInit;
    private final DbRemove dbRemove;

    @GetMapping(value = UrlConstants.SYNCHRONIZE)
    public ResponseEntity<ApiResponseMessage> synchronize() {
        ApiResponseMessage message = new ApiResponseMessage();
        try {
            dbRemove.dbRemove();
            dbInit.dbInit();

            message.setStatus("Success");
            message.setMessage("주소록 동기화 성공");

        }catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
