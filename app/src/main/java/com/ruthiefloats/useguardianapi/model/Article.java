package com.ruthiefloats.useguardianapi.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContributorWebTitle() {
        return contributorWebTitle;
    }

    public void setContributorWebTitle(String contributorWebTitle) {
        this.contributorWebTitle = contributorWebTitle;
    }

    private String webTitle;
    private String webUrl;
    private String thumbnail;
    private String contributorWebTitle;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public static Creator<Article> getCREATOR() {
        return CREATOR;
    }

    private Bitmap bitmap;

    public Article(String webTitle, String webUrl, String thumbnail, String contributorWebTitle) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.thumbnail = thumbnail;
        this.contributorWebTitle = contributorWebTitle;
    }

    public Article(){}

    @Override
    public String toString() {
        return "Article Title :" + webTitle + "\n"
                +"Author: " + contributorWebTitle + "\n"
                +"Thumbnail: " + thumbnail + "\n"
                +"WebURL: " + webUrl;
    }

    protected Article(Parcel in) {
        webTitle = in.readString();
        webUrl = in.readString();
        thumbnail = in.readString();
        contributorWebTitle = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webTitle);
        dest.writeString(webUrl);
        dest.writeString(thumbnail);
        dest.writeString(contributorWebTitle);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}