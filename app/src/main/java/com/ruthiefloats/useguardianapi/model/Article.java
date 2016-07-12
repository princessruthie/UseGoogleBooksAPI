package com.ruthiefloats.useguardianapi.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Article class has five instance variables, the associated getters and
 * boilerplate Parcelable implementation
 */

public class Article implements Parcelable {


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
    /**
     * Each Article has a title, a contributor, a webUrl and a thumbnail
     * url.
     */
    private String webTitle;
    private String webUrl;
    private String contributorWebTitle;
    private String thumbnail;
    private Bitmap bitmap;


    /**
     * @param webTitle            the title
     * @param webUrl              the location on the web
     * @param thumbnail           the location of a thumbnail
     * @param contributorWebTitle the contributor who wrote the Article
     */
    public Article(String webTitle, String webUrl, String thumbnail, String contributorWebTitle) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.thumbnail = thumbnail;
        this.contributorWebTitle = contributorWebTitle;
    }

    public Article() {
    }

    protected Article(Parcel in) {
        webTitle = in.readString();
        webUrl = in.readString();
        thumbnail = in.readString();
        contributorWebTitle = in.readString();
    }

    public static Creator<Article> getCREATOR() {
        return CREATOR;
    }

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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /*
    For easier debugging.
     */
    @Override
    public String toString() {
        return "Article Title :" + webTitle + "\n"
                + "Author: " + contributorWebTitle + "\n"
                + "Thumbnail: " + thumbnail + "\n"
                + "WebURL: " + webUrl;
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
}