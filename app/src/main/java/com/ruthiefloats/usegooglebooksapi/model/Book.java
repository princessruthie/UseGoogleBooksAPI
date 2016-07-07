package com.ruthiefloats.usegooglebooksapi.model;

public class Book {

    /**
     * Currently a Book object has a String for title, a String for
     * author, getters and setters.
     */

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
