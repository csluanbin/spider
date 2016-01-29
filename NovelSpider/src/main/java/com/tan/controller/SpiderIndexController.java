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
	
	@RequestMapping
	public ModelAndView add(@RequestParam(value = "url") String url, @RequestParam(value = "encode") String encode){
		System.out.println("this is spiderindex");
		ModelAndView mav = new ModelAndView("spider");
		String str;
		try 
		{
			//String url="http://www.23wx.com/html/12/12217/";
			//String encode="gbk";
			str = htmlservice.ExtractFromUrl(url, encode);
			String[] strs=new String[1];
			strs[0]="a";
			List<Node> list=htmlservice.GetNode(str, "L", strs, "class");
			int len=list.size();
			int num=(len/5);
			if((len%5)!=0)
			{
				num=num+1;
			}
			mav.addObject("num", num);
			mav.addObject("size", len);
			mav.addObject("list", list);
			servletContext.setAttribute("query_list", list);
			servletContext.setAttribute("url", url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
}
