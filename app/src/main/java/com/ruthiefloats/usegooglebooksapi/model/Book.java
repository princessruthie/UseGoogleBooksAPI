package com.ruthiefloats.usegooglebooksapi.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    /**
     * Currently a Book object has a String for title, a String for
     * author, getters and setters.
     */
    private String mAuthor;
    private String mTitle;

    /**
     * @param author Author(s) of the book
     * @param title  Title of the book
     */
    public Book(String author, String title) {
        mTitle = title;
        mAuthor = author;
    }

    public Book() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthor);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book(Parcel in) {
        mTitle = in.readString();
        mAuthor = in.readString();
    }

    @Override
    public String toString() {
        return "Title: " + mTitle + "\n"
                + "Author: " + mAuthor + "\n";
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }


    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }
}