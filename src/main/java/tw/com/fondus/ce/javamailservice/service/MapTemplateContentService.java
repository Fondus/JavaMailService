package tw.com.fondus.ce.javamailservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public interface MapTemplateContentService {

	Map<String, Object> patchJsonData( JsonNode data ) throws JsonProcessingException;
}
