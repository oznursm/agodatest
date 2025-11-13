package com.web.base.pageElement;

public class BeinLabel extends PageElementModel {

    public BeinLabel(selectorNames selectorName, String selectorValue) {
        super(selectorName, selectorValue);
    }

    public String getLabelText(){
        return getWebElement().getText();
    }
}
