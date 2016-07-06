package com.ruthiefloats.usegooglebooksapi.model;

/**
 * Created by fieldsru on 7/5/16.
 */
public class Book {
    private String mTitle;

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    private String mAuthor;

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

}
