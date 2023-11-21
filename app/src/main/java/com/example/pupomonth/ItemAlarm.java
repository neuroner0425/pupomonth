package com.example.pupomonth;

public class ItemAlarm {
    private int dDay;
    private String comment;
    private String title;

    public int getDDay(){
        return dDay;
    }

    public String getComment(){
        return comment;
    }

    public String getTitle(){
        return title;
    }

    public void setDDay(int dDay){
       this.dDay = dDay;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setTitle(String title){
        this.title = title;
    }

    ItemAlarm(){}
    ItemAlarm(int dDay, String comment, String title){
        this.dDay=dDay;
        this.comment = comment;
        this.title = title;
    }
}
