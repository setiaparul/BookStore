package com.assignment.bookstore;

import com.assignment.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    Book book;
    @BeforeEach
    public void setup(){
        book = new Book("123456789","1","100","Ramu","Hello World",1);
    }

    @Test
    public void getTitle() throws Exception {
        assertEquals("Hello World", book.getTitle());
    }

    @Test
    public void setTitle() throws Exception {
        book.setTitle("newTitle");
        assertEquals("newTitle", book.getTitle());
    }

    @Test
    public void getAuthor() throws Exception {
        assertEquals("Ramu", book.getAuthor());
    }

    @Test
    public void setAuthor() throws Exception {
        book.setAuthor("newAuthor");
        assertEquals("newAuthor", book.getAuthor());
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals("100", book.getPrice());
    }

    @Test
    public void setPrice() throws Exception {
        book.setPrice("200");
        assertEquals("200", book.getPrice());
    }
    @Test
    public void getISBN() throws Exception {
        assertEquals("123456789", book.getIsbn());
    }

    @Test
    public void setISBN() throws Exception {
        book.setIsbn("2345678");
        assertEquals("2345678", book.getIsbn());
    }
}
