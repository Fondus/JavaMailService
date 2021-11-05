package tw.com.fondus.ce.javamailservice.service.exception;

public class ContentGenerationException extends Exception {
	public ContentGenerationException( String message ) {
		super( message );
	}

	public ContentGenerationException( String message, Exception cause ) {
		super( message, cause );
	}
}
