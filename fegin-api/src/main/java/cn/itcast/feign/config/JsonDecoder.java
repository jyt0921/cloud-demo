package cn.itcast.feign.config;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import cn.itcast.feign.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import feign.Response;
import feign.codec.Decoder;

public class JsonDecoder implements Decoder {
    private final ObjectMapper objectMapper;

    public JsonDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.body() == null) {
            return null;
        }
        if (type instanceof Class && ((Class<?>) type).isAssignableFrom(User.class)) {
            return objectMapper.readValue(response.body().asInputStream(), User.class);
        } else if (type instanceof ParameterizedType && ((ParameterizedType) type).getRawType() == List.class) {
            Type itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, (Class<?>) itemType);
            return objectMapper.readValue(response.body().asInputStream(), collectionType);
        }
        throw new RuntimeException("Unsupported type: " + type);
    }
}
