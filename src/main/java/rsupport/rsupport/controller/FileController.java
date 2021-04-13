package rsupport.rsupport.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.rsupport.construct.Construct;
import rsupport.rsupport.util.DbInit;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final DbInit dbInit;

    @GetMapping(value = Construct.FILE)
    public void readFile() {
        dbInit.db_init();
    }

}
