package com.pourtoujours.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pourtoujours.api.IImageService;
import com.pourtoujours.base.Consumer;
import com.pourtoujours.model.Gallery;
import com.pourtoujours.util.JsonUtil;

import java.util.List;

public class ImageLogicService {
    IImageService imageService = Consumer.getImageService();

    public static JsonArray parseList(List<Gallery> galleryList) {
        JsonArray ja = new JsonArray();
        for(Gallery gallery : galleryList){
            JsonObject jo = JsonUtil.parseObject2Json(gallery);
            ja.add(jo);
        }
        return ja;
    }
}
