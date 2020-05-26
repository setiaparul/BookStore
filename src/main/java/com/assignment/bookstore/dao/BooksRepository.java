package com.assignment.bookstore.dao;

import com.assignment.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface BooksRepository extends JpaRepository<Book,String> {

    @Async
    @Query("SELECT t from Book t where t.title like %:title% and  t.author LIKE %:author%")
    Book findByTitleAndAuthorContaining(@Param("title")String title,@Param("author")String author);

    @Async
    @Query("SELECT t FROM Book t where t.title = :title")
    List<Book> findByTitle(@Param("title") String title);

    @Async
    Book findByIsbn(String isbn);

    @Async
    List<Book> findByAuthor(String author);

}
