
package rsupport.addressbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.dto.ApiResponseMessage;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.AddressBookSynchronizeService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AddressBookSynchronizeController {

    @Autowired
    private final AddressBookSynchronizeService addressBookSynchronizeService;

    @GetMapping(value = UrlConstants.SYNCHRONIZE)
    public ResponseEntity<ApiResponseMessage> synchronize() {
        ApiResponseMessage message = new ApiResponseMessage();

        addressBookSynchronizeService.runSync();

        Map<String, Object> data = new HashMap<>();

        message.setStatus("200");
        message.setMessage("주소록 동기화 성공");
        message.setData(data);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
