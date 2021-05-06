package rsupport.addressbook.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class PropertyUtils {

    @Value("${custom.addressbook.file.path}")
    private String addressbookFilePath;
}
