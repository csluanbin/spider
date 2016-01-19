package com.tan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tan.model.Book;
import com.tan.service.BookService;

@Controller
@RequestMapping
public class IndexController {

	private BookService bookService;
	@RequestMapping(value = "/*", method = RequestMethod.GET)
    public String index() {
		System.out.println("this is index");
        return "index";
    }
	
	public BookService getBookService() {
		return bookService;
	}
	@Resource
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
}