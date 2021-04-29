
package rsupport.addressbook.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class PropertyUtils {

    private final Environment environment;

    public String getAddressbookFilePath() {
        return environment.getProperty("addressbook.file.path");
    }
}
