package tw.com.fondus.ce.javamailservice.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ByteArrayResource;

@Data @Builder public class Attachment {
	private String name;
	private String mimeType;
	private ByteArrayResource data;
}

