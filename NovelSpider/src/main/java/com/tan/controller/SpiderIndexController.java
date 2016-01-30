package com.tan.controller;

import com.tan.model.Next;
import com.tan.model.Node;
import com.tan.model.Root;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	@InitBinder("root")  
    public void initBinder1(WebDataBinder binder) {  
		binder.setFieldDefaultPrefix("root.");  
    }
	@InitBinder("next")  
    public void initBinder2(WebDataBinder binder) {  
		binder.setFieldDefaultPrefix("next.");  
    }
	
	@RequestMapping
	public ModelAndView Add(@ModelAttribute("root") Root root, @ModelAttribute("next") Next next){
		//System.out.println(root.toString());
		//System.out.println(next.toString());
		ModelAndView mav = new ModelAndView("spider");
		String str;
		try 
		{
			str = htmlservice.ExtractFromUrl(root.getUrl(), root.getEncode());
			
			String top_elements=root.getElements();
			String[] strs=top_elements.split(";");
			
			List<Node> list=htmlservice.GetNode(str, root.getTag(), strs, root.getType());
			int len=list.size();
			int num=(len/5);
			if((len%5)!=0)
			{
				num=num+1;
			}
			mav.addObject("num", num);
			mav.addObject("size", len);
			mav.addObject("list", list);
			
			String next_elements=next.getElements();
			String next_tag=next.getTag();
			String next_id=next.getType();
			
			servletContext.setAttribute("query_list", list);
			servletContext.setAttribute("url", root.getUrl());
			servletContext.setAttribute("next_id", next_id);
			servletContext.setAttribute("next_tag", next_tag);
			servletContext.setAttribute("encode", root.getEncode());
			
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
