package com.liangtengyu.markdown.entity;

import lombok.Data;

/**
 * 实体类
 *
 * 只有一个实体对象，不需要区分VO、DTO 那么复杂，一个 POJO 就可以搞定
 */
@Data
public class MarkDown {

    private String website;

    private String blogUrl;

    private String imagePath;

    private String imageUrl;

    private String imageName;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
