package com.example.santo.youtrends2.app.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Santo on 28/04/2015.
 */
public class Trend implements Parcelable {

    private String id;
    private String title;
    private String urlThumb;
    private String position;
    private Date publishedAt;
    private String description;
    private String viewCount;
    private String likeCount;
    private String dislikeCount;

    private String region;

    private int categoryId;
    private int trendDifference;






    public Trend() {}

    public Trend(Parcel source) {
        id=source.readString();
        title=source.readString();
        urlThumb=source.readString();
        position=source.readString();
        /*long dateMillis=source.readLong();
        publishedAt=(dateMillis == -1 ? null : new Date(dateMillis));
        description=source.readString();
        viewCount=source.readString();
        likeCount=source.readString();
        dislikeCount=source.readString();*/
        //TODO: completare

    }

    public Trend(String id,
                 String title,
                 String urlThumb,
                 String position,
                 Date publishedAt,
                 String description,
                 String viewCount,
                 String likeCount,
                 String dislikeCount) {
        this.id = id;
        this.title = title;
        this.urlThumb = urlThumb;
        this.position = position;
        this.publishedAt = publishedAt;
        this.description = description;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrlThumb(String urlThumb) {
        this.urlThumb = urlThumb;
    }

    public void setPosition(String position) { this.position = position; }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlThumb() {
        return urlThumb;
    }

    public String getPosition() {
        return position;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }


    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(String dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTrendDifference() {
        return trendDifference;
    }

    public void setTrendDifference(int trendDifference) {
        this.trendDifference = trendDifference;
    }

    @Override
    public String toString() {
        return "ID: "+id+
                "title: "+title+
                "urlThumb: "+urlThumb;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(urlThumb);
        dest.writeString(position);
        /*dest.writeLong(publishedAt == null ? -1 : publishedAt.getTime());
        dest.writeString(description);
        dest.writeString(viewCount);
        dest.writeString(likeCount);
        dest.writeString(dislikeCount);*/
        //TODO completare

    }

    public static final Parcelable.Creator<Trend> CREATOR = new Parcelable.Creator<Trend>(){
        @Override
        public Trend createFromParcel(Parcel source) {
            return new Trend(source);
        }

        @Override
        public Trend[] newArray(int size) {
            return new Trend[size];
        }
    };

}
