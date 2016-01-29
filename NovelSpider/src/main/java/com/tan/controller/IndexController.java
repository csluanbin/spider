package com.tan.controller;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tan.model.Book;
import com.tan.service.BookService;

@Controller
@RequestMapping("/index.do")
public class IndexController {

	private BookService bookService;
	
	@Autowired 
	private HttpServletRequest request;
	
	@RequestMapping
    public String index(HttpSession httpSession) {
		httpSession.setAttribute("login", true);
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