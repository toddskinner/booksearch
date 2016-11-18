package com.example.android.booksearch;

/**
 * Created by toddskinner on 11/17/16.
 */

public class Book {
    private String mTitle;
    private String mBookCover;
    private String mAuthor;
    private String mDescription;
    private String mInfoLink;

    public Book (String title, String author, String description, String infoLink, String smallThumbnailUrl){
        mTitle = title;
        mAuthor = author;
        mDescription = description;
        mInfoLink = infoLink;
        mBookCover = smallThumbnailUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getAuthor(){
        String authorLessQuotations = mAuthor.replace("\"", " ");
        String authorLessCommas = authorLessQuotations.replace(",", "&");
        return "By" + " " + authorLessCommas.substring(2 , authorLessCommas.length()-2);
    }

    public String getBookCover(){
        return mBookCover;
    }

    public String getDescription() { return mDescription; }

    public String getInfoLink() { return mInfoLink; }
}
