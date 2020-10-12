package co.com.gamestore.framework.response;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author Jonathan.Barrera
 * Class to handle the response to requester 
 * @param <T>
 */

public class BaseResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;
	private List<?> list;
	private String errorMessage;
	
	public BaseResponse() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

	@Override
	public String toString() {
		return "BaseResponse [success=" + success + ", list=" + list + ", errorMessage=" + errorMessage + "]";
	}
	
	

}
