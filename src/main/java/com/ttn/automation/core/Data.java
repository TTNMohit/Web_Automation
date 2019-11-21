package com.ttn.automation.core;

import java.util.Map;

/**
 * The Object representation of data used for testing .
 *
 * @author abhishek sharma
 */

public class Data {

    private String suiteName;
    private Map<String, DataElements> dataList;

    public Data()
    {

    }
    public Data(String testSuiteName, Map<String, DataElements> dataList) {
        this.suiteName = testSuiteName;
        this.dataList = dataList;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public Map<String, DataElements> getElementList() {
        return dataList;
    }

    public void setElementList(Map<String, DataElements> elementList) {
        this.dataList = elementList;
    }


}


