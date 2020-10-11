package co.com.gamestore.framework.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static ObjectMapper objectMapper;
	
	public JsonUtils() {
		super();
	}
	/**
	 * Get ObjectMapper, this method use a double checked pattern to prevent the initialization until first instance
	 * @return
	 */
	private static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapperlocal = objectMapper;
		if (objectMapperlocal == null) {
			synchronized (JsonUtils.class) {
				objectMapperlocal = objectMapper;
				if (objectMapperlocal == null) {
					try {
						objectMapper = new ObjectMapper();
						objectMapper.configure(MapperFeature.USE_STD_BEAN_NAMING, true); //imponer compatibilidad estricta
						objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //fallo al encontrar una propiedad desconocida
						objectMapper.configure(com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true); //incluir caracteres inferiores aal ASCII con código 32
						objectMapper.configure(com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true); // permitir nombres de atributos sin comillas
						objectMapper.configure(com.fasterxml.jackson.core.json.JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true); // permitir comillas simples
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
				
			}
		}
		return objectMapper;
	}
	/**
	 * Method to get a Map from JSON string
	 * @param jsonData
	 * @return a Map with keys of a JSON string
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJson(String jsonData) throws Exception{
		return getObjectFromJson(jsonData,Map.class);
	}
	
	/**
	 * Method to get a specific object from a JSON string
	 * @param <T>
	 * @param jsonData
	 * @param classToParse
	 * @return a specific object from a JSON string
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static <T> T getObjectFromJson(String jsonData, Class<T> classToParse) throws JsonMappingException, JsonProcessingException {
		return getObjectMapper().readValue(jsonData, classToParse);
	}
	/**
	 * Method to get a specific object from a Map
	 * @param <T>
	 * @param map
	 * @param classToParse
	 * @return a object of specific class from a Map
	 */
	public static <T> T getObjectFromMap(Map<String,Object>  map, Class<T> classToParse) {
		return getObjectMapper().convertValue(map, classToParse);
	}
	/**
	 * 
	 * @param obj
	 * @param onlyNonEmpty
	 * @param singleQuoted
	 * @return Json string from any object
	 * @throws JsonProcessingException
	 */
	public static String getJsonFromObject(Object object) throws JsonProcessingException {
		return getObjectMapper().writer().writeValueAsString(object);
	}

}
