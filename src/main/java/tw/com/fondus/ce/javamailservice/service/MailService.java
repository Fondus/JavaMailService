package tw.com.fondus.ce.javamailservice.service;

import tw.com.fondus.ce.javamailservice.entity.MailInfo;
import tw.com.fondus.ce.javamailservice.vo.Attachment;

public interface MailService {
	String emailTo( MailInfo mailInfo, String subject, String from, String content, CONTENT_BODY_TYPE contentBodyType,
			Attachment... attachments );

	enum CONTENT_BODY_TYPE {
		HTML,
		TEXT;
	}
}

