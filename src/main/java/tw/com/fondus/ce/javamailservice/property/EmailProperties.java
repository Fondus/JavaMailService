package tw.com.fondus.ce.javamailservice.property;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties( value = "fondus.settings.mail" )
public class EmailProperties {
	private String from;
}
