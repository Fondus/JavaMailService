package tw.com.fondus.ce.javamailservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.com.fondus.ce.javamailservice.service.MapTemplateContentService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MapTemplateContentServiceImpl implements MapTemplateContentService {
	@Override
	public Map<String, Object> patchJsonData( JsonNode data ) throws JsonProcessingException {
		Map<String, Object> mapData = new HashMap<>( Map.of( "all-data", data, "body", data.toPrettyString() ) );
		mapData.putAll( new ObjectMapper().readValue( data.toString(), HashMap.class ) );
		mapData.put( "timestamp", OffsetDateTime.now()
				.format( DateTimeFormatter.ofPattern( "yyyy-MM-dd hh:mm" ) ) );
		log.debug( "patchJsonData.mapData: {}", mapData );
		return mapData;
	}
}
