package mx.gapsi.commons.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
    
    public static HashMap<String, String> escapeSpecialChars(HashMap<String, String> parameters) {
		if(parameters==null){
			return null;
		}
		String specialChars = ".+*?^${}()|\\[\\]";
		HashMap<String, String> escapedMap = new HashMap<>();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String escapedValue = value.replaceAll("'", "");
			escapedValue = escapedValue.replaceAll("([" + specialChars + "])", "\\\\$1");
			escapedMap.put(key, escapedValue);
		}
		return escapedMap;
	}

	public static String camelCaseToSnakeCase(String text) {
        return text.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2");
    }
    
}
