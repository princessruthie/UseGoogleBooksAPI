package com.ruthiefloats.usegooglebooksapi.model;

public class Book {

    /**
     *
     * @param author    Author(s) of the book
     * @param title     Title of the book
     */
    public Book(String author, String title){
        mTitle = title;
        mAuthor = author;
    }

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
