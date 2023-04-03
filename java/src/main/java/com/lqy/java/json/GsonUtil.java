package com.lqy.java.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonUtil {
    private static final Gson gson = new Gson();

    /**
     * 将对象转换为json字符串
     * transform object to json string
     * @param obj 对象
     * @return json字符串
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 将json字符串转换为指定类型的对象
     * @param json json字符串
     * @param classOfT 指定类型
     * @return 指定类型的对象
     * @param <T> 指定类型
     */
    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 将json字符串转换为指定类型的对象
     * transform json string to specified type object
     * @param json json string
     * @param typeOfT type of object
     * @return object
     * @param <T> type of object
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 将json字符串转换为JsonObject
     * transform json string to JsonObject
     * @param json json string
     * @return JsonObject
     */
    public static JsonObject parseJsonObject(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        return jsonElement.getAsJsonObject();
    }

    /**
     * 将Map转换为JsonObject
     * transform Map to JsonObject
     * @param map map
     * @return JsonObject
     */
    public static JsonObject mapToJsonObject(Map<String, Object> map) {
        String json = toJson(map);
        return parseJsonObject(json);
    }

    public static void main(String[] args) {
        String json = "{\"name\":\"lqy\",\"age\":18}";
        JsonObject jsonObject = parseJsonObject(json);
        System.out.println(jsonObject.get("name").getAsString());
        System.out.println(jsonObject.get("age").getAsInt());

        // test fromJson
        Map<String, Object> map = fromJson(json, Map.class);
        System.out.println(map.get("name"));
        System.out.println(map.get("age"));

        // test toJson
        System.out.println(toJson(map));

        // test mapToJsonObject
        System.out.println(mapToJsonObject(map));

        // test fromJson
        System.out.println(fromJson(toJson(map), Map.class));

        // test fromJson
        System.out.println(fromJson(toJson(map), JsonObject.class));
    }
}
