package com.pourtoujours.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
    public static JsonObject string2Json(String jstr){
        if(StringUtil.isNullOrEmpty(jstr)){
            return null;
        }else {
            return new JsonParser().parse(jstr).getAsJsonObject();
        }
    }

    public static JsonObject newFailureJson(String msg){
        JsonObject retJson = new JsonObject();
        retJson.addProperty("retCode",9999);
        retJson.addProperty("retMsg",msg);
        return  retJson;
    }

    public static JsonObject newSucessJson(String msg){
        JsonObject retJson = new JsonObject();
        retJson.addProperty("retCode",0);
        retJson.addProperty("retMsg",msg);
        return  retJson;
    }

    public static JsonObject parseObject2Json(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return string2Json(json);
    }

    public static String getString(JsonObject object, String param){
        JsonElement obj =  object.get(param);
        if(obj != null){
            return object.get(param).getAsString();
        }
        return null;
    }

    public static int getInt(JsonObject object, String param) {
        JsonElement obj =  object.get(param);
        if(obj != null){
            return object.get(param).getAsInt();
        }
        return 0;
    }

    public static int getInt(JsonObject object, String param,int defaultValue) {
        JsonElement obj =  object.get(param);
        if(obj != null){
            return object.get(param).getAsInt();
        }
        return defaultValue;
    }

    public static boolean getBoolean(JsonObject json, String param) {
        JsonElement obj =  json.get(param);
        if(obj != null){
            return json.get(param).getAsBoolean();
        }
        return false;
    }

    public static boolean getBoolean(JsonObject json, String param, boolean defaultValue) {
        JsonElement obj =  json.get(param);
        if(obj != null){
            return json.get(param).getAsBoolean();
        }
        return defaultValue;
    }
     /*{
                    // errno 即错误代码，0 表示没有错误。
                    //       如果有错误，errno != 0，可通过下文中的监听函数 fail 拿到该错误码进行自定义处理
                    "errno": 0,

                        // data 是一个数组，返回若干图片的线上地址
                        "data": [
                    "图片1地址",
                            "图片2地址",
                            "……"
    ]
                }*/
     public static JSONObject newImageRetJson(int errno,JSONArray data){
         JSONObject retJson = new JSONObject();
         retJson.put("errno",errno);
         if(data == null){
            data = new JSONArray();
         }
         retJson.put("data",data);
         return  retJson;
     }
}
