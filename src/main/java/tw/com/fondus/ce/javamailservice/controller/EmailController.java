package tw.com.fondus.ce.javamailservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.fondus.ce.javamailservice.entity.MailInfo;
import tw.com.fondus.ce.javamailservice.property.EmailProperties;
import tw.com.fondus.ce.javamailservice.service.ContentService;
import tw.com.fondus.ce.javamailservice.service.MailService;
import tw.com.fondus.ce.javamailservice.service.MapTemplateContentService;
import tw.com.fondus.ce.javamailservice.service.exception.ContentGenerationException;
import tw.com.fondus.ce.javamailservice.service.exception.MailInfoNotFoundException;
import tw.com.fondus.ce.javamailservice.vo.APIResult;
import tw.com.fondus.ce.javamailservice.vo.Content;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping( "/emails" )
public class EmailController {
	private final EmailProperties emailProperties;
	private final ContentService contentService;
	private final MailService mailService;
	private final MapTemplateContentService mapTemplateContentService;
	private final ObjectMapper mapper;

	public EmailController( EmailProperties emailProperties,
			ContentService contentService,
			MailService mailService,
			MapTemplateContentService mapTemplateContentService,
			ObjectMapper mapper ){
		this.emailProperties = emailProperties;
		this.contentService = contentService;
		this.mailService = mailService;
		this.mapTemplateContentService = mapTemplateContentService;
		this.mapper = mapper;
	}

	@PostMapping( "/templates/{templateName}" )
	public APIResult<String> sendEmailUsingTemplate( @PathVariable String templateName, @RequestBody JsonNode data ) {
		try {
			Content content = this.contentService.generate( templateName, this.mapTemplateContentService.patchJsonData( data ) );
			JsonNode info = data.get( "info" );
			if ( Objects.isNull( info ) )
				throw new MailInfoNotFoundException( "Mail Info not found." );
			MailInfo mailInfo = this.mapper.readValue( info.toString(), MailInfo.class );
			if ( content.isHtml() ) {
				return APIResult.success(
						this.mailService.emailTo( mailInfo, content.getSubject(), this.emailProperties.getFrom(),
								content.getHtml(), MailService.CONTENT_BODY_TYPE.HTML ) );
			} else {
				return APIResult.success(
						this.mailService.emailTo( mailInfo, content.getSubject(), this.emailProperties.getFrom(),
								content.getText(), MailService.CONTENT_BODY_TYPE.TEXT ) );
			}
		} catch (ContentGenerationException | JsonProcessingException | MailInfoNotFoundException e) {
			log.error( "Failed to send email.", e );
			return APIResult.fail( e );
		}
	}
}
