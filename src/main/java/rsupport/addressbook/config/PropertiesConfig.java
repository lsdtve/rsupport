package rsupport.addressbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import rsupport.addressbook.util.PropertyUtils;

@Configuration
@PropertySource("classpath:application.yml")
public class PropertiesConfig {

	@Bean
	public PropertyUtils propertiesUtil() {
		return new PropertyUtils();
	}

}
