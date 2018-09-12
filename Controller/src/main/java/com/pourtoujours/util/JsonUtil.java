package com.pourtoujours.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
}
