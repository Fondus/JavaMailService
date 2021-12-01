package tw.com.fondus.ce.javamailservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.fondus.ce.javamailservice.config.EmailConfiguration;
import tw.com.fondus.ce.javamailservice.entity.MailInfo;
import tw.com.fondus.ce.javamailservice.service.ContentService;
import tw.com.fondus.ce.javamailservice.service.MailService;
import tw.com.fondus.ce.javamailservice.service.MapTemplateContentService;
import tw.com.fondus.ce.javamailservice.service.exception.ContentGenerationException;
import tw.com.fondus.ce.javamailservice.service.exception.MailInfoNotFoundException;
import tw.com.fondus.ce.javamailservice.vo.APIResult;
import tw.com.fondus.ce.javamailservice.vo.Content;

import javax.mail.MessagingException;

@RestController
@RequestMapping( "/emails" )
@Slf4j
public class EmailController {
	@Autowired
	EmailConfiguration emailConfiguration;
	@Autowired
	ContentService contentService;
	@Autowired
	MailService mailService;
	@Autowired
	MapTemplateContentService mapTemplateContentService;

	@PostMapping( "/templates/{templateName}" )
	public APIResult<String> sendEmailUsingTemplate( @PathVariable String templateName, @RequestBody JsonNode data ) {
		try {
			Content content = contentService.generate( templateName, mapTemplateContentService.patchJsonData( data ) );
			ObjectMapper mapper = new ObjectMapper();
			JsonNode info = data.get( "info" );
			if ( info == null )
				throw new MailInfoNotFoundException( "Mail Info not found." );
			MailInfo mailInfo = mapper.readValue( info.toString(), MailInfo.class );
			if ( content.isHtml() ) {
				return APIResult.success(
						mailService.emailTo( mailInfo, content.getSubject(), emailConfiguration.getFrom(),
								content.getHtml(), MailService.CONTENT_BODY_TYPE.HTML ) );
			} else {
				return APIResult.success(
						mailService.emailTo( mailInfo, content.getSubject(), emailConfiguration.getFrom(),
								content.getText(), MailService.CONTENT_BODY_TYPE.TEXT ) );
			}
		} catch (MessagingException | ContentGenerationException | JsonProcessingException | MailInfoNotFoundException e) {
			e.printStackTrace();
			return APIResult.fail( e );
		}
	}
}
