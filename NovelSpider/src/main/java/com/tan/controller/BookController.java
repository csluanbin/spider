package com.tan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tan.model.Book;
import com.tan.service.BookService;

@Controller
@RequestMapping("/book.do")
public class BookController {

	private BookService bookService;
	@RequestMapping(params = "method=add")
	public ModelAndView add(){
		ModelAndView mav = new ModelAndView("main");
		Book p1 = new Book();  
	    p1.setId(1);  
	    p1.setName("luanbin");
	    mav.addObject("person",p1);
		//System.out.println("bookname:"+book.getName());
		//System.out.println("author:"+book.getAuthor());
		//bookService.add(book);
		return mav;
	}
	@RequestMapping(params = "method=update")
	public String update(Book book) {
		bookService.update(book);
		return "success";
	}
	public BookService getBookService() {
		return bookService;
	}
	@Resource
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
}