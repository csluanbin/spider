package com.tan.tool;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tan.model.Node;

public class HtmlExtractServiceImple {
	public String ExtractFromUrl (String str_url, String encode) throws Exception
	{
		HttpClient httpclient = new DefaultHttpClient();
		//httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,  "Mozilla/5.0 (Windows NT 6.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1");
        HttpGet httpget = new HttpGet(str_url);
        HttpResponse res = httpclient.execute(httpget);
        HttpEntity entity = res.getEntity();
        String loginEntityContent = EntityUtils.toString(entity, encode);
        return loginEntityContent;
	}
	
	public List<Node> GetNode(String html,String id, String[] strs, String type)
	{
		Document doc=  Jsoup.parse(html);
		List<Node> list=new ArrayList<Node>();
		switch(type)
		{
			case "class":
				Elements elements=doc.getElementsByClass(id);
		        for(Element e: elements)
		        {
		        	List<Element> list_element=GetElementList(e, strs);
		        	for(Element ele: list_element)
		        	{
		        		Node node=new Node();
		        		node.setLink(ele.attr("href"));
		        		node.setText(ele.html());
		        		list.add(node);
		        	}
		        }
		        break;
			case "id":
				Element element=doc.getElementById(id);
		        List<Element> list_element=GetElementList(element, strs);
		    	for(Element ele: list_element)
		    	{
		    		Node node=new Node();
		    		node.setLink(ele.attr("href"));
		    		node.setText(ele.html());
		    		list.add(node);
		    	}
		}
		return list;
	}
	
	public List<Node> GetNodeContent(String html,String id, String[] strs, String type)
	{
		Document doc=  Jsoup.parse(html);
		List<Node> list=new ArrayList<Node>();
		switch(type)
		{
			case "class":
				Elements elements=doc.getElementsByClass(id);
		        for(Element e: elements)
		        {
		        	List<Element> list_element=GetElementList(e, strs);
		        	for(Element ele: list_element)
		        	{
		        		Node node=new Node();
		        		node.setText(ele.html());
		        		list.add(node);
		        	}
		        }
		        break;
			case "id":
				Element element=doc.getElementById(id);
		        List<Element> list_element=GetElementList(element, strs);
		    	for(Element ele: list_element)
		    	{
		    		Node node=new Node();
		    		node.setText(ele.html());
		    		list.add(node);
		    	}
		}
		return list;
	}
	
	private List<Element> GetElementList(Element e, String[] tags)
	{
		List<Element> list=new ArrayList<Element>();
		if(tags.length==0)
		{
			list.add(e);
		}
		else
		{
			Elements temp=e.getElementsByTag(tags[0]);
			if(temp==null)
			{
				list.add(e);
				return list;
			}
			for(int i=1; i<tags.length; i++)
			{
				String str=tags[i];
				Elements temp1=new Elements();
				for(Element e1:temp)
				{
					temp1.addAll(e1.getElementsByTag(str));
				}
				if(temp1.size()==0)
				{
					list.add(e);
					return list;
				}
				temp=temp1;
			}
			for(Element e1:temp)
			{
				list.add(e1);
			}
		}
		return list;
	}
}
