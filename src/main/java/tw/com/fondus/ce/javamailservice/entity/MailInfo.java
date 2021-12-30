package tw.com.fondus.ce.javamailservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailInfo {
	@Builder.Default
	List<String> to = new ArrayList<>();
	@Builder.Default
	List<String> cc = new ArrayList<>();
	@Builder.Default
	List<String> bcc = new ArrayList<>();
}
