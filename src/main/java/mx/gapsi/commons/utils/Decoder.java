package mx.gapsi.commons.utils;

import java.util.Base64;
import java.lang.reflect.Type;
import com.google.gson.Gson;

public class Decoder {
    
    public static <T> T base64Unpack(String base64, Type ObjectType) {
        return new Gson().fromJson(new String(Base64.getDecoder().decode(base64.getBytes())), ObjectType);
    }
    
}
