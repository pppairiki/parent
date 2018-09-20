package com.pourtoujours.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pourtoujours.api.IFileService;
import com.pourtoujours.base.Consumer;
import com.pourtoujours.model.CCFile;
import com.pourtoujours.model.CCFileContent;
import com.pourtoujours.util.JsonUtil;

import java.util.List;

public class FileLogicService {
    IFileService fileService = Consumer.getFileService();

    public static JsonArray parseList(List<CCFile> fileList) {
        JsonArray ja = new JsonArray();
        for(CCFile file : fileList){
            JsonObject jo = JsonUtil.parseObject2Json(file);
            ja.add(jo);
        }
        return ja;
    }

    public static JsonArray parseFileContentList(List<CCFileContent> fileListContent) {
        JsonArray ja = new JsonArray();
        for(CCFileContent file : fileListContent){
            JsonObject jo = JsonUtil.parseObject2Json(file);
            ja.add(jo);
        }
        return ja;
    }
}
