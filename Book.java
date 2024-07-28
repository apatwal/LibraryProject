package com.example.finalproject;

public class Book {
    String Url;
    String Author;
    String Title;

    public Book(String url, String author, String title) {
        Url = url;
        Author = author;
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
