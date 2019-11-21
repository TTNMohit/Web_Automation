package com.ttn.automation.core;

import java.util.Map;

/**
* The Object representation of a page in the app.
* @author abhishek sharma*/
public class Page {
	
	private String pageName;
	private Map<String, PageElement> elementList;
	
	public Page(String pageName, Map<String, PageElement> elementList) {
		this.pageName = pageName;
		this.elementList = elementList;
	}
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public Map<String, PageElement> getElementList() {
		return elementList;
	}
	public void setElementList(Map<String, PageElement> elementList) {
		this.elementList = elementList;
	}
	

}
