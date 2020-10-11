package co.com.gamestore.framework.error;

public class CustomErrorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomErrorException(String message) {
		super(message);
	}

}
