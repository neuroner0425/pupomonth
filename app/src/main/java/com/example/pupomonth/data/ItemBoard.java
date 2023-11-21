package com.example.pupomonth.data;

public class ItemBoard {
    private String title;
    private String imgURL;

    public String getTitle(){return title;}
    public String getImgURL(){return imgURL;}

    public void setTitle(String title){
        this.title = title;
    }

    ItemBoard(){}
    ItemBoard(String title, String imgURL){
        this.title = title;
        this.imgURL = imgURL;
    }
}
