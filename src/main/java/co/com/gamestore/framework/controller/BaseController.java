package co.com.gamestore.framework.controller;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

import co.com.gamestore.framework.business.BaseBusiness;
import co.com.gamestore.framework.error.CustomErrorException;
import co.com.gamestore.framework.repository.BaseRepository;
import co.com.gamestore.framework.util.Utils;

/**
* @author Jonathan.Barrera
*
*/
@SuppressWarnings({"rawtypes", "unchecked" })
public class BaseController<T extends BaseBusiness, B extends BaseRepository> {
	private T business;
	public BaseController() throws CustomErrorException {
		try {
			configureMVC(
				(Class)(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]),
			    (Class)(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1])
			);
		} catch (InstantiationException e) {
			throw new CustomErrorException("Ha ocurrido un error instancionado las clases: "+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new CustomErrorException("Error al intentar crear una instancia de forma reflexiva: "+ e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new CustomErrorException("No se ha encontrado una clase mientras se realizaban las instancias de forma reflexiva: " + e.getMessage());
		}
	}
	/**
	 * Setup class with reflection
	 * @param businessClass
	 * @param repositoryClass
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("deprecation")
	private void configureMVC(Class<T> businessClass, Class<B> repositoryClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (Utils.isNull(business)) {
			synchronized (BaseController.class) {
				if (Utils.isNull(business)) {
					setBusiness(businessClass.newInstance());
					getBusiness().setRepository((repositoryClass == null || repositoryClass.getName().equals("co.com.gamestore.framework.repository.BaseRepository")) ? (BaseRepository) Class.forName("co.com.gamestore.framework.repository.BaseRepository").newInstance() : (BaseRepository) repositoryClass.newInstance());
				}				
			}			
		}
	}
	
	public T getBusiness() {
        return business;
    }
	
	public void setBusiness(T business) {
        this.business = business;
    }
}
