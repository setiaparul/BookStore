package com.assignment.bookstore.controller;

import com.assignment.bookstore.model.Book;
import com.assignment.bookstore.model.BookBuyReq;
import com.assignment.bookstore.model.Response;
import com.assignment.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/books")
public class BookStoreController {

    BookStoreService bookService;
    Response clientResponse;

    @Autowired
    public void setClientResponse(Response clientResponse) {
        this.clientResponse = clientResponse;
    }

    @Autowired
    public void setBookService(BookStoreService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Response> addBook(@RequestBody  Book book){
        boolean isAdded = bookService.addBook(book);
        if(!isAdded){
            clientResponse.setMessage("One of the andatory params (title,isbn,author,price) is missing in request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(clientResponse);
        }
        clientResponse.setMessage("Book with title "+book.getTitle()+" and author "+book.getAuthor()+" added.");
      return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchByTitle(@RequestParam(defaultValue = "title") String title,
                                    @RequestParam(defaultValue = "author") String author,
                                    @RequestParam(defaultValue = "isbn") String isbn) {
        clientResponse.setMessage("Books Fetched are : ");
        try {
            if(!title.equals("title")){
                clientResponse.setObj(bookService.searchByTitle(title).get());
            }else if(!author.equals("author")){
                clientResponse.setObj(bookService.searchByAuthor(author).get());
            }else if(!isbn.equals("isbn")){
                clientResponse.setObj(bookService.searchByISBN(isbn).get());
            }
        } catch (InterruptedException e) {
            clientResponse.setMessage("Exception While Fetching Books, See Logs for more Details");
            e.printStackTrace();
        } catch (ExecutionException e) {
            clientResponse.setMessage("Exception While Fetching Books, See Logs for more Details");
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
    }
    @GetMapping("/getMediaCoverage")
    public ResponseEntity<Response> findMediaCoverage(@RequestParam(defaultValue = "isbn") String isbn){
        try {
        clientResponse.setMessage("Fetched Media Coverge of Book with isbn number :"+isbn +"are :");
        clientResponse.setObj(bookService.getMediaCoverage(isbn).get());
        } catch (InterruptedException e) {
            clientResponse.setMessage("Exception While Fetching Media Coverge of Book, See Logs for more Details");
            e.printStackTrace();
        } catch (ExecutionException e) {
            clientResponse.setMessage("Exception While Fetching Media Coverge of Book, See Logs for more Details");
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
    }

    @PostMapping("/buy")
    public ResponseEntity<Response> buyBook(@RequestBody BookBuyReq req){
        boolean isBookbought = false;
            isBookbought =  bookService.buyBook(req.getTitle(),req.getAuthor());
        if(isBookbought){
            clientResponse.setMessage("Book with title"+ req.getTitle() +"and Author"+req.getAuthor()+"is bought.");
            return ResponseEntity.status(HttpStatus.OK).body(clientResponse);
        }else{
            clientResponse.setMessage("Book with title"+ req.getTitle() +"and Author"+req.getAuthor()+"is not available.");
            return ResponseEntity.status(HttpStatus.OK).body(clientResponse );
        }
    }
}
