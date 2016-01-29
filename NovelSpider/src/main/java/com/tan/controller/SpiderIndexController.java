package com.tan.controller;

import com.tan.model.Node;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tan.model.Book;
import com.tan.tool.HtmlExtractServiceImple;

@Controller
@RequestMapping("/spiderindex.do")
public class SpiderIndexController {
	
	private int num=5;
	
	@Autowired
	private HtmlExtractServiceImple htmlservice;
	@Autowired 
	private ServletContext servletContext;
	@Autowired 
	private HttpServletRequest request;
	
	@RequestMapping
	public ModelAndView add(@RequestParam(value = "url") String url, @RequestParam(value = "encode") String encode){
		System.out.println("this is spiderindex");
		ModelAndView mav = new ModelAndView("spider");
		String str;
		try 
		{
			str = htmlservice.ExtractFromUrl(url, encode);
			
			String top_elements=request.getParameter("top_elements");
			String[] strs=top_elements.split(";");
			
			List<Node> list=htmlservice.GetNode(str, request.getParameter("top_tag"), strs, request.getParameter("top"));
			int len=list.size();
			int num=(len/5);
			if((len%5)!=0)
			{
				num=num+1;
			}
			mav.addObject("num", num);
			mav.addObject("size", len);
			mav.addObject("list", list);
			
			String next_elements=request.getParameter("next_elements");
			String[] next_strs=next_elements.split(";");
			String next_tag=request.getParameter("next_tag");
			String next_id=request.getParameter("next");
			
			servletContext.setAttribute("query_list", list);
			servletContext.setAttribute("url", url);
			servletContext.setAttribute("next_id", next_id);
			servletContext.setAttribute("next_tag", next_tag);
			servletContext.setAttribute("encode", encode);
			
			if((next_elements==null)||(next_elements.trim().isEmpty()))
			{
				servletContext.setAttribute("next_elements", new String[0]);
			}
			else
			{
				servletContext.setAttribute("next_elements", next_elements.split(";"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
