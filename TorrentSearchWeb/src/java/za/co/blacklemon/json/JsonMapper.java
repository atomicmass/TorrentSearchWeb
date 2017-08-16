package za.co.blacklemon.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonMapper<T> {
    
    private TypeReference<T> typeRef;
    
    public JsonMapper(TypeReference<T> typeRef) {
        this.typeRef = typeRef;
    }

    public T mapJason(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T ret = null;
        try {
            ret = mapper.readValue(json, typeRef);
        } catch (IOException ex) {
            Logger.getLogger(JsonMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
