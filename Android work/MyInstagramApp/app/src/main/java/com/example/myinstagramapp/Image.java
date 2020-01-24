package com.example.myinstagramapp;

public class Image {
    public String key;
    public String userId;
    public String downloadUrl;

    public User user;

    public int likes = 0;

    public boolean hasLiked = false;

    public String userLike;

    public void addLikes()
    {
        this.likes++;
    }

    public void removeLikes()
    {
        this.likes--;
    }

    public Image() {

    }

    public Image(String key, String userId, String downloadUrl) {

        this.key = key;
        this.userId = userId;
        this.downloadUrl = downloadUrl;
    }

}