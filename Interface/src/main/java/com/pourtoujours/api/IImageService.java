package com.pourtoujours.api;

import com.pourtoujours.model.Gallery;

import java.util.List;

public interface IImageService {
    List<Gallery> getGalleryList(int userId, int pageNum, int pageSize);
}
