package tw.com.fondus.ce.javamailservice.service.impl;

import com.jayway.jsonpath.JsonPath;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.fondus.ce.javamailservice.config.TemplateConfiguration;
import tw.com.fondus.ce.javamailservice.service.ContentService;
import tw.com.fondus.ce.javamailservice.service.exception.ContentGenerationException;
import tw.com.fondus.ce.javamailservice.vo.Content;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
@Slf4j
public class FreeMarkerContentServiceImpl implements ContentService {
	@Autowired
	Configuration configuration;
	@Autowired
	TemplateConfiguration templateConfiguration;

	@Override
	public Content generate( String template, Map<String, Object> data ) throws ContentGenerationException {
		try {
			String subject = process( template + "/subject.ftl", data );
			if ( subject == null )
				subject = template;
			String templateVersion = JsonPath.parse( data.toString() )
					.read( templateConfiguration.getFolder() );
			String text = process( template + "/" + templateVersion + "/text.ftl", data );
			String html = process( template + "/" + templateVersion + "/html.ftl", data );
			if ( text == null && html == null )
				throw new ContentGenerationException( "template not found" );
			return Content.builder()
					.subject( subject )
					.text( text )
					.html( html )
					.build();
		} catch (IOException | TemplateException e) {
			log.error( "Fail to generate html content using template " + template, e );
			throw new ContentGenerationException( "IOException", e );
		}
	}

	private String process( String template, Object data ) throws IOException, TemplateException {
		try {
			Template freeMarkerTemplate = configuration.getTemplate( template );
			StringWriter output = new StringWriter();
			freeMarkerTemplate.process( data, output );
			return output.toString();
		} catch (TemplateNotFoundException e) {
			return null;
		}
	}
}
