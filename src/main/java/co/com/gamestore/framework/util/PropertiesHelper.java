package co.com.gamestore.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesHelper {
	
	private static Map<String,Properties> properties = new HashMap<String,Properties>();
	
	public static synchronized Properties getProperties(String name) {
		try {
			Properties prop = properties.get(name);
			if (null == prop) {
				prop = addProperties(name);
			}
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static synchronized Properties addProperties(String name) throws IOException {
		Properties prop = new Properties();
		InputStream is;
		try {
			is = new FileInputStream(Constants.BASE_PROP_PATH+name);
			prop.load(is);
			is.close();
			properties.put(name, prop);
		}catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return prop;
	}

}
