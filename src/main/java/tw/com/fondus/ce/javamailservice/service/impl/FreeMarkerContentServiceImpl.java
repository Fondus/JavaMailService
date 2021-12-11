package tw.com.fondus.ce.javamailservice.service.impl;

import com.jayway.jsonpath.JsonPath;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.fondus.ce.javamailservice.property.TemplateProperties;
import tw.com.fondus.ce.javamailservice.service.ContentService;
import tw.com.fondus.ce.javamailservice.service.exception.ContentGenerationException;
import tw.com.fondus.ce.javamailservice.vo.Content;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FreeMarkerContentServiceImpl implements ContentService {
	private final Configuration configuration;
	private final TemplateProperties templateProperties;

	public FreeMarkerContentServiceImpl( Configuration configuration,
			TemplateProperties templateProperties ){
		this.configuration = configuration;
		this.templateProperties = templateProperties;
	}

	@Override
	public Content generate( String template, Map<String, Object> data ) throws ContentGenerationException {
		try {
			String subject = process( template + "/subject.ftl", data )
					.orElse( template );
			String templateVersion = JsonPath.parse( data.toString() )
					.read( templateProperties.getFolder() );
			Optional<String> text = process( template + "/" + templateVersion + "/text.ftl", data );
			Optional<String> html = process( template + "/" + templateVersion + "/html.ftl", data );
			if ( text.isPresent() && html.isPresent() ) {
				return Content.builder()
						.subject( subject )
						.text( text.get() )
						.html( html.get() )
						.build();
			} else {
				throw new ContentGenerationException( "template not found" );
			}
		} catch (IOException | TemplateException e) {
			log.error( "Fail to generate html content using template " + template, e );
			throw new ContentGenerationException( "IOException", e );
		}
	}

	private Optional<String> process( String template, Object data ) throws IOException, TemplateException {
		try {
			Template freeMarkerTemplate = configuration.getTemplate( template );
			StringWriter output = new StringWriter();
			freeMarkerTemplate.process( data, output );
			return Optional.of( output.toString() );
		} catch (TemplateNotFoundException e) {
			return Optional.empty();
		}
	}
}
