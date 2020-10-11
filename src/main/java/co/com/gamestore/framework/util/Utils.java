/**
 * 
 */
package co.com.gamestore.framework.util;

/**
 * @author Jonathan.Barrera
 *
 */
public class Utils {
	/**
	 * method to replace a empty string with a specific value
	 * @param value
	 * @param defaultValue
	 * @return the first string or the second value if the first is null or empty
	 */
    public static String nvl(String value, String defaultValue){
        if(value.isEmpty()) 
        	value = defaultValue;
        return value;
    }
    /**
     * Method to know if a object is null or if a string is empty
     * @param value
     * @return true if the object is null or false if the object is not null
     */
    public static Boolean isNull(Object value) {
    	if (null == value)
    		return true;
    	else if (value instanceof String && value.equals(""))
    		return true;
    	return false;
    }
}
