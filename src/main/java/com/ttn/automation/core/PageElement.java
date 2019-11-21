package com.ttn.automation.core;
/**
* The Object representation of a page element in the app.
* @author abhishek sharma
*/
public class PageElement {
	
	public PageElement(String elementName, String locatorType, String locatorValue) {
		this.elementName = elementName;
		this.locatorType = locatorType;
		LocatorValue = locatorValue;
	}

	private String elementName,locatorType,LocatorValue;

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getLocatorType() {
		return locatorType;
	}

	public void setLocatorType(String locatorType) {
		this.locatorType = locatorType;
	}

	public String getLocatorValue() {
		return LocatorValue;
	}

	public void setLocatorValue(String locatorValue) {
		LocatorValue = locatorValue;
	}
	
	@Override
	public String toString() {
		return "PageElements [elementName=" + elementName + ", locatorType=" + locatorType + ", LocatorValue="
				+ LocatorValue + "]";
	}

}
