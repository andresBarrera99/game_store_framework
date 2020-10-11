package co.com.gamestore.framework.business;

import co.com.gamestore.framework.repository.BaseRepository;
import co.com.gamestore.framework.response.BaseResponse;

/**
 * @author Jonathan.Barrera
 *
 */
public class BaseBusiness < B extends BaseRepository> {
	private B repository;
	
	@SuppressWarnings("deprecation")
	protected <T extends BaseResponse> T handleError(Exception e, Class<T> responseClass) throws ReflectiveOperationException {
		T obj = null;
		try {
			obj = responseClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			throw e1;
		} 
		obj.setSuccess(false);
		String msg = e.getMessage();
		if (e instanceof NullPointerException) {
			msg = "Null value error";
		}
		else if (msg.toLowerCase().contains("foreign key constraint fails")) {
			msg = "Could not delete or update the data";
		}else if ( e instanceof NumberFormatException) {
			msg = "The number "+ e.getMessage() + " is invalid";
		}
		obj.setErrorMessage(msg);
		return obj;
	}
	
	public B getRepository() {
		return repository;
	}
	public void setRepository(B repository) {
		this.repository = repository;
	}
	
	

}
