package edu.umkc.mobile.assignment4;

/**
 * Created by vinuthna on 17-11-2017.
 */

public class Book {

    private String title;

    private String subtitle;

    private String authors;

    private String publishedDate;

    private String image;

    Book(String title, String subtitle, String authors, String publishedDate, String image){
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
