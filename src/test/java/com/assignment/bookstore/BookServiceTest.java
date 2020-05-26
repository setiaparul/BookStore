package com.assignment.bookstore;

import com.assignment.bookstore.dao.BooksRepository;
import com.assignment.bookstore.model.Book;
import com.assignment.bookstore.service.BookStoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceTest {
    Book book;
    @Mock
    BooksRepository booksdao;
    @InjectMocks
    BookStoreService service;
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @BeforeEach
    public void setup(){
        //service = new BookStoreService();
        book = new Book("12345678910","1","100","Ramu","Hello World",1);
    }

    @Test
    public void addBook() throws Exception {
        assertEquals(true, service.addBook(book));
    }

    @Test
    public void buyBook() throws Exception {
        assertEquals(false, service.buyBook("eum et","ABC"));
    }
}
