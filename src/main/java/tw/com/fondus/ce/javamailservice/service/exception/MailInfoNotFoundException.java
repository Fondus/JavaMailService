package tw.com.fondus.ce.javamailservice.service.exception;

public class MailInfoNotFoundException extends Exception {
	public MailInfoNotFoundException( String message ) {
		super( message );
	}

	public MailInfoNotFoundException( String message, Exception cause ) {
		super( message, cause );
	}
}
