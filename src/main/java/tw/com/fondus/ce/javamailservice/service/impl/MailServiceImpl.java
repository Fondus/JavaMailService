package tw.com.fondus.ce.javamailservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tw.com.fondus.ce.javamailservice.entity.MailInfo;
import tw.com.fondus.ce.javamailservice.service.MailService;
import tw.com.fondus.ce.javamailservice.vo.Attachment;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
	private final JavaMailSender emailSender;

	public MailServiceImpl( JavaMailSender emailSender ){
		this.emailSender = emailSender;
	}

	@Override
	public String emailTo( MailInfo mailInfo, String subject, String from, String content,
			CONTENT_BODY_TYPE contentBodyType, Attachment... attachments ) {
		try {
			if ( Objects.isNull( mailInfo ) || mailInfo.getTo()
					.isEmpty() )
				return "not-sent";

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper( message, true, "UTF-8" );
			helper.setFrom( from );
			helper.setTo( mailInfo.getTo()
					.toArray( new String[0] ) );
			helper.setCc( mailInfo.getCc()
					.toArray( new String[0] ) );
			helper.setBcc( mailInfo.getBcc()
					.toArray( new String[0] ) );
			helper.setSubject( subject );
			helper.setText( content, contentBodyType == CONTENT_BODY_TYPE.HTML );

			for ( Attachment attachment : attachments ) {
				helper.addAttachment( attachment.getName(), attachment.getData(), attachment.getMimeType() );
			}
			emailSender.send( message );
		} catch (MessagingException e) {
			log.error( "Send email has something wrong.", e );
		}
		return "ok";
	}
}
