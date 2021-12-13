package tw.com.fondus.ce.javamailservice.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Content {
	private String subject;
	private String text;
	private String html;

	public boolean isHtml() {
		return html != null && !html.isBlank();
	}
}
