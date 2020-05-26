package com.assignment.bookstore.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "books")
@EqualsAndHashCode(of = {"isbn","author"})
public class Book {
    @Column(name = "no_of_copies")
    private int no_of_copies;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private String price;
    @Column(name = "edition")
    private String edition;
    @Id
    @Column(name = "isbn")
    private String isbn;

    public Book(){

    }
    public Book(String isbn, String edition,String price,String author,String title,int no_of_copies) {
        setTitle(title);
        setAuthor(author);
        setEdition(edition);
        setPrice(price);
        setIsbn(isbn);
        setNo_of_copies(no_of_copies);
    }
}
