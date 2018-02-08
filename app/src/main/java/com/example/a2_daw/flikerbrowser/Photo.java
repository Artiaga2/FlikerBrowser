package com.example.a2_daw.flikerbrowser;

/**
 * Created by 2_DAW on 08/02/2018.
 */

public class Photo {

    private String mTitle;
    private String mLink;
    private String mAuthor;
    private String mAuthorId;
    private String mImage;
    private String mTags;

    public Photo(String mTitle, String mLink, String mAuthor, String mAuthorId, String mImage, String mTags) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mImage = mImage;
        this.mTags = mTags;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmTags() {
        return mTags;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mTags='" + mTags + '\'' +
                '}';
    }
}
