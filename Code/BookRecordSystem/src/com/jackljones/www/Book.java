// Package deceleration
package com.jackljones.www;

// This class models a Book object

public class Book {

    // Instance Variables
    private String id;
    private String isbn;
    private String title;
    private String author;
    private String pages;
    private String genre;
    private String review;

    // Used for TableView
    private int pagesInt;
    private int idInt;

    // Constructor 1 (Pass Each Variable In Manually)
    public Book(String id, String isbn, String title, String author, String pages, String genre, String review) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.genre = genre;
        this.review = review;

        // Used for TableView
        pagesInt = Integer.valueOf(this.pages);
        idInt = Integer.valueOf(this.id);
    }

    // Constructor 2 (Pass Variables In Through Array)
    public Book(String[] details) {
        this.id = details[0];
        this.isbn = details[1];
        this.title = details[2];
        this.author = details[3];
        this.pages = details[4];
        this.genre = details[5];
        this.review = details[6];

        // Used for TableView
        pagesInt = Integer.valueOf(this.pages);
        idInt = Integer.valueOf(this.id);
    }

    // Getter and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getPagesInt(){
        return  pagesInt;
    }

    public void setPagesInt(int pagesInt){
        this.pagesInt = pagesInt;
    }

    public int getIdInt(){
        return  idInt;
    }

    public void setIdInt(int idInt){
        this.idInt = idInt;
    }

    // To String Method
    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages='" + pages + '\'' +
                ", genre='" + genre + '\'' +
                ", review='" + review + '\'' +
                '}';
    }
}
