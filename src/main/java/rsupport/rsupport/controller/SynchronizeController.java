
package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.constant.UrlConstants;
import rsupport.rsupport.util.DbInit;
import rsupport.rsupport.util.DbRemove;

@RestController
@RequiredArgsConstructor
public class SynchronizeController {

    private final DbInit dbInit;
    private final DbRemove dbRemove;

    @GetMapping(value = UrlConstants.SYNCHRONIZE)
    public void synchronize() {
        dbRemove.dbRemove();
        dbInit.dbInit();
    }

}
