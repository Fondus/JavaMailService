package tw.com.fondus.ce.javamailservice.config;

import freemarker.template.Configuration;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FreeMarkerConfiguration {
	@Bean
	public Configuration getFreeMarkerConfiguration() {
		Configuration configuration = new Configuration( Configuration.VERSION_2_3_29 );
		configuration.setClassLoaderForTemplateLoading( FreeMarkerConfiguration.class.getClassLoader(),
				"/freemarker-templates" );
		configuration.setDefaultEncoding( "UTF-8" );
		configuration.setObjectWrapper( new Java8ObjectWrapper( Configuration.VERSION_2_3_29 ) );
		return configuration;
	}
}
