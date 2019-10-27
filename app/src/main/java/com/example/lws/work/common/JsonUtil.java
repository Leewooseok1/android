package com.example.lws.work.common;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class JsonUtil {
    // JSON(제이슨)은 XML에 비해 경량화된 데이터 교환포맷으로 데이터 교환을 쉽게 할 수 있습니다.
    // 기본적으로 key : value 형태로 데이터를 표현

    // GSON 라이브러리(구글 제공)는 Java Object를 JSON으로 또는, JSON을 Java Object로의 변환

    public static <T> JSONObject getJsonObjectFromMap(Map<String, T> map) {
        try {
            JSONObject json = new JSONObject(); // jsonobject 생성
            for (Map.Entry<String, T> entry : map.entrySet()) {
                // hashmap = Map 인터페이스의 한종류로써 Key와 Value 값으로 데이터를 저장하는 형태
                // entrySet(hashmap에 저장된 값을 엔트리형태로 set에 저장하여 반환)에 있는 값들을 entry에 하나씪 넣는다.
                String key = entry.getKey(); // 입장ket를 key에 주고
                T value = entry.getValue(); //입장value를 value에 준다.
                json.put(key, value); //key와 value를 json에 놓는다.
            }
            return json; // 되돌려준다.

        } catch (JSONException e) {
            e.printStackTrace(); // 예외 결과를 화면에 출력한다.
        }

        return null;
    }

    public static JSONObject getJsonObjectFromObject(Object object) {
        Gson gson = new Gson(); //gson 생성
        JSONObject jsonObject = null; // jsonobject가 없으면
        try {
            jsonObject = new JSONObject(gson.toJson(object)); // jsonobject 생성한다.
        } catch (JSONException e) {
            e.printStackTrace(); // 예외 결과를 화면에 출력한다.
        }
        return jsonObject; // 되들려준다.
    }

    public static <T> T convertClass(JSONObject jsonObject, Class<T> clazz) {
        return new Gson().fromJson(jsonObject.toString(), clazz); // wrapper에 있는 fromjson의 clazz와 jsonobject를 새되로 돌려준다.
        // return = 어떤 값이나 객체를 되돌려준다.
    }
}
