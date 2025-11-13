package com.web.base.backend;

import com.web.base.model.ApacPackageOffer;
import com.web.base.model.ApacSubscription;
import com.web.base.model.ApacVoucher;

import java.util.HashMap;

public class BaseAutomationContext {
    private static HashMap<ContextKeys, String> contextMap;
    private static ApacSubscription apacSubscription;
    private static ApacPackageOffer apacPackageOffer;
    private static ApacVoucher apacVoucher;

    public static void initializeContext(){
        contextMap = new HashMap<ContextKeys, String>();
    }

    public static void addContext(ContextKeys key, String value){
        if(contextMap == null)
            initializeContext();
        if(contextMap.get(key) != null)
            contextMap.remove(key);
        contextMap.put(key, value);
    }

    public static String getContextValue(ContextKeys key){
        if(contextMap == null)
            return "CONTEXT IS NULL";
        String value = contextMap.get(key);
        if(value == null)
            return "CONTEXT WITH KEY " + key + " NOT FOUND";
        return value;
    }

    public static ApacSubscription getApacSubscription() {
        return apacSubscription;
    }

    public static void setApacSubscription(ApacSubscription apacSubscription) {
        BaseAutomationContext.apacSubscription = apacSubscription;
    }

    public static ApacPackageOffer getApacPackageOffer() {
        return apacPackageOffer;
    }

    public static void setApacPackageOffer(ApacPackageOffer apacPackageOffer) {
        BaseAutomationContext.apacPackageOffer = apacPackageOffer;
    }

    public static ApacVoucher getApacVoucher() {
        return apacVoucher;
    }

    public static void setApacVoucher(ApacVoucher apacVoucher) {
        BaseAutomationContext.apacVoucher = apacVoucher;
    }
}
