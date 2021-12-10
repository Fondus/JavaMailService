package tw.com.fondus.ce.javamailservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tw.com.fondus.ce.javamailservice.entity.MailInfo;
import tw.com.fondus.ce.javamailservice.service.MailService;
import tw.com.fondus.ce.javamailservice.vo.Attachment;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
	@Autowired
	private JavaMailSender emailSender;

	@Override
	public String emailTo( MailInfo mailInfo, String subject, String from, String content,
			CONTENT_BODY_TYPE contentBodyType, Attachment... attachments ) throws MessagingException {
		try {
			if ( mailInfo == null || mailInfo.getTo()
					.isEmpty() )
				return "not-sent";

			MimeMessage message = emailSender.createMimeMessage();

			MimeMessageHelper helper = null;
			helper = new MimeMessageHelper( message, true, "UTF-8" );

			helper.setFrom( from );
			helper.setTo( mailInfo.getTo()
					.toArray( new String[mailInfo.getTo()
							.size()] ) );
			helper.setCc( mailInfo.getCc()
					.toArray( new String[mailInfo.getCc()
							.size()] ) );
			helper.setBcc( mailInfo.getBcc()
					.toArray( new String[mailInfo.getBcc()
							.size()] ) );
			helper.setSubject( subject );
			helper.setText( content, contentBodyType == CONTENT_BODY_TYPE.HTML );

			for ( Attachment attachment : attachments ) {
				helper.addAttachment( attachment.getName(), attachment.getData(), attachment.getMimeType() );
			}
			emailSender.send( message );
		} catch (MessagingException e) {
		}
		return "ok";
	}
}
