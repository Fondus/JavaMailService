package tw.com.fondus.ce.javamailservice.service;

import tw.com.fondus.ce.javamailservice.service.exception.ContentGenerationException;
import tw.com.fondus.ce.javamailservice.vo.Content;

import java.util.Map;

public interface ContentService {
	Content generate(String template,  Map<String, Object> data) throws ContentGenerationException;
}
