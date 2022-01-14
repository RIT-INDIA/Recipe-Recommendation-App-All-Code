package com.example.myrecipe.model;

import com.example.myrecipe.adapter.StudentAdapter;

public class StudentModel {

    private String name;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    //This private field to maintain to every row's state...!
    private boolean isSelected;

    public boolean isSelected() {

        if(StudentAdapter.finallist.contains(name))
        {
            return true;
        }
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
