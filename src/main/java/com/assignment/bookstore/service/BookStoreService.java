package com.assignment.bookstore.service;

import com.assignment.bookstore.dao.BooksRepository;
import com.assignment.bookstore.model.Book;
import com.assignment.bookstore.model.MediaCoverage;
import com.assignment.bookstore.model.MediaCoverageList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class BookStoreService {

    BooksRepository booksdao;

    private static ReentrantLock lock = new ReentrantLock();
    @Autowired
    ObjectMapper objMapper;

    @Autowired
    public void setBooksdao(BooksRepository booksdao) {
        this.booksdao = booksdao;
    }

    private boolean validateBook(Book book){
        if(!StringUtils.isEmpty(book.getIsbn()) && !StringUtils.isEmpty(book.getTitle()) &&
        !StringUtils.isEmpty(book.getAuthor()) && !StringUtils.isEmpty(book.getPrice())){
            return true;
        }
        return false;
    }

    @Async
    public boolean addBook(Book book){
       if(validateBook(book)){
           lock.lock();
           Book bookFromDb = booksdao.findByIsbn(book.getIsbn());
            if(Objects.nonNull(bookFromDb)){
                //Making Thread Safe
                AtomicInteger noOfCopies = new AtomicInteger(bookFromDb.getNo_of_copies());
                bookFromDb.setNo_of_copies(new Integer(noOfCopies.incrementAndGet()));
                booksdao.save(bookFromDb);
            }else{
                book.setNo_of_copies(1);
                booksdao.save(book);
            }
           lock.unlock();
            return true;
       }else{
           return false;
       }

    }

    @Async
    public CompletableFuture<List<Book>> searchByTitle(String title){
        CompletableFuture<List<Book>> books =  CompletableFuture.supplyAsync(()->booksdao.findByTitle(title));
        return books.thenApply(book -> {
            return book.stream().collect(Collectors.toList());
        });
    }

    @Async
    public CompletableFuture<List<Book>> searchByAuthor(String authorName) {
        return CompletableFuture.supplyAsync(()->booksdao.findByAuthor(authorName)).thenApply(books -> {return books.stream().collect(Collectors.toList());});
    }
    @Async
    public CompletableFuture<Book> searchByISBN(String isbn){
        return CompletableFuture.supplyAsync(()->booksdao.findByIsbn(isbn));
    }

    public CompletableFuture<List<String>>  getMediaCoverage(String isbn){
        CompletableFuture<List<MediaCoverage>> mediaCoverageList = CompletableFuture.supplyAsync(()->fetchMediaCoverage());
        CompletableFuture<Book> bookInFuture = searchByISBN(isbn);
        List<String> matchedTitleList = new ArrayList<>();

        CompletableFuture<List<String>> titleList = mediaCoverageList.thenCombineAsync(bookInFuture,(coverageList,book)->{
            String title = book.getTitle();
            return coverageList.stream().filter(coverage->
                coverage.getTitle().contains(title) || coverage.getBody().contains(title)).map(mediaCoverage ->
                mediaCoverage.getTitle() ).collect(Collectors.toList());
        });
        /*if(Objects.nonNull(book)){
            String title = book.getTitle();
            matchedTitleList = mediaCoverageList.stream().filter(coverage->
                coverage.getTitle().contains(title) || coverage.getBody().contains(title)).map(mediaCoverage ->
                mediaCoverage.getTitle() ).collect(Collectors.toList());
            return matchedTitleList;

        }*/
        return titleList;
    }
@Async
    public List<MediaCoverage> fetchMediaCoverage()  {
        String client = "https://jsonplaceholder.typicode.com/posts";

        RestTemplate restTemplate = new RestTemplate();
        String mediaCoverageJson = restTemplate.getForObject(client,String.class);
        try {
            List<MediaCoverage> coverageList =Arrays.asList(objMapper.readValue(mediaCoverageJson,MediaCoverage[].class));
            return coverageList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;

        }
    }

    @Async
    public Boolean buyBook(String bookTitle,String author){
        lock.lock();
        Book book = booksdao.findByTitleAndAuthorContaining(bookTitle,author);
            if(Objects.nonNull(book)){
                updateBookAfterFetching(book);
                lock.unlock();
                return true;
            }
            lock.unlock();
            return false;
    }

    private void updateBookAfterFetching(Book book){
        AtomicInteger noOfCopies = new AtomicInteger(book.getNo_of_copies());
        if(noOfCopies.get() == 1){
            book.setNo_of_copies(10);
        }else{
            book.setNo_of_copies(noOfCopies.decrementAndGet());
        }
        booksdao.save(book);

    }
}
