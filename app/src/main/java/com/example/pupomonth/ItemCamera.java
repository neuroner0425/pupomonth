package com.example.pupomonth;

public class ItemCamera {
    private String title;
    private String date;

    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }


    public void setTitle(String title){
        this.title = title;
    }
    public void setDate(String date){
        this.date = date;
    }

    ItemCamera(String title, String date){
        this.title = title;
        this.date = date;
    }
}
