package com.example.lws.work.common;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jackson(잭슨) =자바 json 라이브러리 ===== 유사품 Gson
 * =  json 뿐만 아니라 XML, YAML, CSV 등 다양한 형식의 데이타를 지원하는 data-processing 툴
 *
 * JSON <-> Object 변환(=파싱)하는 jackson 초기화
 *
 * object -> json(String,file) Serialization 직렬화
 * json(String,file) -> object Deserialization 역직렬화
 */

public class JacksonFactory {

    public static JacksonWrapper create() {
        return new JacksonWrapper();
    }
    // common.jacksonWrapper에서 가져와서 리턴

    public static ObjectMapper createMapper() {
        ObjectMapper objectMapper = new ObjectMapper(); // 객체 매퍼 (디테일 추가)
        //직렬화 안할 때 모든 필드에 대한 가시성 끈다.

        // json -> object 역직렬화
       objectMapper.setVisibility(objectMapper.getDeserializationConfig().getDefaultVisibilityChecker()
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY) // JsonAutoDetect = 데이터 매핑 법칙 변경
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 직렬화 포함x

        // object -> json 직렬화
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withCreatorVisibility(JsonAutoDetect.Visibility.ANY)
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));

//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //역직렬화에 대해 알려지지 않은 속성은 구성x
        return objectMapper;
    }

}
