package com.tan.controller;

import com.tan.model.Node;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tan.model.Book;
import com.tan.tool.HtmlExtractServiceImple;

@Controller
@RequestMapping("/query.do")
public class QueryController {
	
	@Autowired
	private HtmlExtractServiceImple htmlservice;
	@Autowired 
	private ServletContext servletContext;
	
	@RequestMapping
	public ModelAndView add(@RequestParam(value = "queryid") int id){
		System.out.println("you are going to query "+id);
		List<Node> list=(List<Node>) servletContext.getAttribute("query_list");
		ModelAndView mav = new ModelAndView("query");
		String url=servletContext.getAttribute("url")+list.get(id).getLink();
		String encode=(String) servletContext.getAttribute("encode");
		try 
		{
			String str = htmlservice.ExtractFromUrl(url, encode);
			String[] strs=new String[0];
			List<Node> nodes=htmlservice.GetNode(str, (String)servletContext.getAttribute("next_tag"), (String[])servletContext.getAttribute("next_elements"), (String)servletContext.getAttribute("next_id"));
			String content="";
			for(Node node:nodes)
			{
				content=content+node.getText();
				mav.addObject("id", content);
			}
		} catch (Exception e) {
			mav.addObject("id", "error");
			e.printStackTrace();
		}
		return mav;
	}
}
