package tw.com.fondus.ce.javamailservice.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( value = "templates" )
@Data
@NoArgsConstructor
public class TemplateConfiguration {
	String name;
	String folder;
}
