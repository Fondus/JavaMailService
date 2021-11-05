package tw.com.fondus.ce.javamailservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.fondus.ce.javamailservice.config.EmailConfiguration;
import tw.com.fondus.ce.javamailservice.service.ContentService;
import tw.com.fondus.ce.javamailservice.service.MailService;
import tw.com.fondus.ce.javamailservice.service.exception.ContentGenerationException;
import tw.com.fondus.ce.javamailservice.vo.APIResult;
import tw.com.fondus.ce.javamailservice.vo.Content;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping( "/emails" )

public class EmailController {
	@Autowired EmailConfiguration emailConfiguration;
	@Autowired ContentService contentService;
	@Autowired MailService mailService;

	@PostMapping( "/templates/{templateName}/to/{recipients}" ) public APIResult<String> sendEmailUsingTemplate(
			@PathVariable String templateName, @PathVariable List<String> recipients, @RequestBody JsonNode data ) {
		try {
			Content content = contentService.generate( templateName,
					Map.of( "data", data, "body", data.toPrettyString() ) );
			if ( content.isHtml() ) {
				return APIResult.success(
						mailService.emailTo( recipients, content.getSubject(), emailConfiguration.getFrom(),
								content.getHtml(), MailService.CONTENT_BODY_TYPE.HTML ) );
			} else {
				return APIResult.success(
						mailService.emailTo( recipients, content.getSubject(), emailConfiguration.getFrom(),
								content.getText(), MailService.CONTENT_BODY_TYPE.TEXT ) );
			}
		} catch (MessagingException | ContentGenerationException e) {
			e.printStackTrace();
			return APIResult.fail( e );
		}
	}
}
