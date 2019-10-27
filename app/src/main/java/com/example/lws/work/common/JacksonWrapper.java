package com.example.lws.work.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Jackson 사용이 어려워, 쉽게 만들기 위해 사용한 Wrapper(포장)
 */

public class JacksonWrapper {
    private ObjectMapper objectMapper;

    public JacksonWrapper() {
        objectMapper = JacksonFactory.createMapper();
    } // Mapper = 형식변환

    // try - catch 예외처리
    // 기능은 같지만, 사용하는 타입이 다를때, Generic 타입

    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            if (json == null) // json이 없으면 리턴x
                return null;
            return objectMapper.readValue(json, clazz); // 매퍼에서 값 읽어와라 (json, clazz)
        } catch (IOException e) {
            e.printStackTrace(); // 예외 결과를 화면에 출력한다.
            return null;
        }
    }

    /**
     * JSON -> List 등 generic을 사용하는 object로 변환할때 쓰임
     * 예 : JacksonFactory.create().fromJson(userInfo.user_social, TypeFactory.defaultInstance().constructCollectionType(List.class, UserSocial.class));
     *
     * @param json object로 변환할 string
     * @param type 변환할 타입 예: TypeFactory.defaultInstance().constructCollectionType(List.class, UserSocial.class)
     * @return 결과 Object
     */

    public <T> T fromJson(String json, JavaType type) {
        try {
            if (json == null)
                return null;
            return objectMapper.readValue(json, type); // 매퍼에서 값 읽어와라 (json, type)
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object); // 매퍼에서 값을 문자열로 쓸거다 (object)
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
};
