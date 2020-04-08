package com.douglas.bean;

public class ServiceCategory {
    private String Category;
    private String Thumbnail;

    public ServiceCategory(String Category, String Thumbnail) {
        this.Category = Category;
        this.Thumbnail = Thumbnail;
    }

    public String  getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String  thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
