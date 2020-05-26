package com.assignment.bookstore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class BookBuyReq {

    String title;
    String author;
}
