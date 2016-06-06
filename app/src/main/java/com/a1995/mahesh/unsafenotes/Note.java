package com.a1995.mahesh.unsafenotes;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mahesh on 5/6/16.
 */
public class Note {
    private UUID mId;
    private String mTitle;
    private String mCategory;
    private Date mDate;
    private String mContent;

    public Note(){
        this (UUID.randomUUID());
    }

    public Note(UUID uuid){
        this.mId = uuid;
        this.mDate = new Date();
    }

    //setters
    public void setDate(Date Date) {
        this.mDate = Date;
    }

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    //getters
    public Date getDate() {
        return mDate;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getCategory() {
        return mCategory;
    }

    public UUID getId() {
        return mId;
    }

    public String getContent() {
        return mContent;
    }

    public String getHintString(){
        int length = mContent.length();
        if(length < 30){
            return mContent;
        }else {
            return mContent.substring(0,29);
        }
    }
}
