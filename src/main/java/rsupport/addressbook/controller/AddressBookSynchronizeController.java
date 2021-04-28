
package rsupport.addressbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.addressbook.constant.UrlConstants;
import rsupport.addressbook.service.AddressBookSynchronizeService;

@RestController
@RequiredArgsConstructor
public class AddressBookSynchronizeController {

    private final AddressBookSynchronizeService addressBookSynchronizeService;

    @GetMapping(value = UrlConstants.SYNCHRONIZE)
    public ResponseEntity<String> synchronize() {
        addressBookSynchronizeService.runSync();

        return ResponseEntity.ok().build();
    }
}
