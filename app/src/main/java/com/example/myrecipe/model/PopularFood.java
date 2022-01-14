package com.example.myrecipe.model;

public class PopularFood {

   private String name;

   private String imageUrl;

    /*public PopularFood(String name, String imageUrl) {
        this.name = name;

        this.imageUrl = imageUrl;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
