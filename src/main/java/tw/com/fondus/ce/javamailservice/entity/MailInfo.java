package tw.com.fondus.ce.javamailservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class MailInfo {
	List<String> to;
	List<String> cc;
	List<String> bcc;
}
