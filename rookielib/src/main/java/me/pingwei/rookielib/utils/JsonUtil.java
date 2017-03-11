package me.pingwei.rookielib.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xupingwei on 2016/6/25.
 * 将json格式的字符串转换成map存储
 */
public class JsonUtil {

    /**
     * Json 转成 Map<>
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getMapForJson(String jsonStr) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LoggerUtils.e(e.toString());
        }
        return null;
    }

    /**
     * Json 转成 List<Map<>>
     *
     * @param jsonStr
     * @return
     */
    public static List<Map<String, Object>> getListForJson(String jsonStr) {
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObj;
            list = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.get(i);
                list.add(getMapForJson(jsonObj.toString()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取JsonObject
     *
     * @param json
     * @return
     */
    public static JSONObject getJsonObject(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取json字符串
     *
     * @param json
     * @param key
     * @return
     */
    public static String getJsonString(String json, String key) {
        try {
            json = json.replace("null", "-");
            JSONObject object = new JSONObject(json);
            String s = object.optString(key);
            return s;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

}
