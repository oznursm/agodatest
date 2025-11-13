package com.web.base.model;

import java.math.BigDecimal;

public class ApacPackageOffer {
    Integer id;
    String countryCode;
    String packageType;
    String offerType;
    Integer packageOrder;
    Integer offerOrder;
    Boolean isTrial;
    String price;
    String trialPrice;
    Boolean isRecurring;
    String expireRule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public Integer getPackageOrder() {
        return packageOrder;
    }

    public void setPackageOrder(Integer packageOrder) {
        this.packageOrder = packageOrder;
    }

    public Integer getOfferOrder() {
        return offerOrder;
    }

    public void setOfferOrder(Integer offerOrder) {
        this.offerOrder = offerOrder;
    }

    public Boolean getTrial() {
        return isTrial;
    }

    public void setTrial(Boolean trial) {
        isTrial = trial;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrialPrice() {
        return trialPrice;
    }

    public void setTrialPrice(String trialPrice) {
        this.trialPrice = trialPrice;
    }

    public Boolean getRecurring() {
        return isRecurring;
    }

    public void setRecurring(Boolean recurring) {
        isRecurring = recurring;
    }

    public String getExpireRule() {
        return expireRule;
    }

    public void setExpireRule(String expireRule) {
        this.expireRule = expireRule;
    }
}
