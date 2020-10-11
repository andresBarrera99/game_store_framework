package co.com.gamestore.framework.business;

import co.com.gamestore.framework.controller.BaseController;
import co.com.gamestore.framework.repository.BaseRepository;

/**
 * @author Jonathan.Barrera
 *
 */
public class BaseBusiness <T extends BaseController<?,?>, B extends BaseRepository> {
	private T controller;
	private B repository;
	public T getController() {
		return controller;
	}
	public void setController(T controller) {
		this.controller = controller;
	}
	public B getRepository() {
		return repository;
	}
	public void setRepository(B repository) {
		this.repository = repository;
	}
	
	

}
