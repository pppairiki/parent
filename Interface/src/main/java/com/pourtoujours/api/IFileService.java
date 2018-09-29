package com.pourtoujours.api;

import com.pourtoujours.common.Page;
import com.pourtoujours.model.CCFile;
import com.pourtoujours.model.CCFileContent;

import java.util.List;

public interface IFileService {
    List<CCFile> getFileList(int userId, int pageNum, int pageSize);

    int save(CCFile fileObject);

    int saveFileContent(CCFileContent fileContent);

    int createFileWithContent(int userId, String userName, String name, String summary, int isPrivate, String content) throws Exception;

    List<CCFileContent> getFileContentList(int id) throws Exception;

    int updateFileContent(int userId, String userName, int id, String name, String summary, int isPrivate, String content) throws Exception;

    int deleteFile(int userId, String userName, int id);

    Page<CCFile> getFilePage(int userId, int pageNum, int pageSize);

    CCFile getFile(int id);

    Page<CCFile> getPublicFilePage(int pageNum, int pageSize);
}
