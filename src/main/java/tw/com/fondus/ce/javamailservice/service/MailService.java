package tw.com.fondus.ce.javamailservice.service;

import tw.com.fondus.ce.javamailservice.vo.Attachment;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {
	String emailTo( List<String> emailTo, String subject, String from, String content,
			CONTENT_BODY_TYPE contentBodyType, Attachment... attachments ) throws MessagingException;

	enum CONTENT_BODY_TYPE {
		HTML, TEXT;
	}
}

