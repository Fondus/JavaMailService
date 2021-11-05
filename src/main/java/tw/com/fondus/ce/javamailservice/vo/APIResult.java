package tw.com.fondus.ce.javamailservice.vo;

import lombok.Builder;
import lombok.Data;

@Data @Builder public class APIResult<T> {
	private int code;
	private T data;
	private String error;

	public static <S> APIResult<S> success( S data ) {
		return APIResult.<S>builder().data( data ).code( 0 ).build();
	}

	public static <S> APIResult<S> fail( Exception ex ) {
		return APIResult.<S>builder().code( -1 ).error( ex.toString() ).build();
	}

}
