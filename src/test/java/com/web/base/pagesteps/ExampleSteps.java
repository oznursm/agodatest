package com.web.base.pagesteps;

import com.thoughtworks.gauge.Step;
import com.web.base.pages.BaseMasterPage;
import com.web.base.pages.ExamplePage;
import com.web.base.utils.driver.Driver;

public class ExampleSteps extends BaseSteps {

    @Step("Antalya value girilir")
    public void searchText() {
        BaseMasterPage searchText = new BaseMasterPage() {
            public void typeSearchKeyword(){
                super.typeSearchKeyword();
            }
        };
        searchText.typeSearchKeyword();

    }
    @Step("İlk çıkan Antalya önerisine tıkla")
    public void clickFirstAntalyaOption() {
        // Page örneği oluştur
        BaseMasterPage page = new BaseMasterPage() { };

        // İlk Antalya’ya tıkla
        page.clickFirstAntalyaSuggestion();
    }
    @Step("Tarih aralığını 1 ile 6 Temmuz 2026 olarak seç")
    public void selectJulyRange() {
        BaseMasterPage page = new BaseMasterPage() { };
        page.selectDateRange_1to6_July2026();
    }

    @Step("çocuk sayısını arttır")
    public void increaseOccupancy() {
        BaseMasterPage page = new BaseMasterPage() { };
        page.increaseRoomOccupancy();
    }
    @Step("Çocuk yaşını 6 seç")
    public void selectChildAgeSix() {
        BaseMasterPage page = new BaseMasterPage() { };
        page.selectChildAge6();
    }


    @Step("Ara butonuna tıkla")
    public void clickSearch() {
        BaseMasterPage page = new BaseMasterPage() { };
        page.clickSearchButton();
    }

    @Step("Tüm otel filtrelerini uygula")
    public void applyAllHotelFiltersStep() {
        BaseMasterPage page = new BaseMasterPage() { };
        page.applyAllHotelFilters();
    }
    @Step("İlk çıkan otele tıkla")
    public void clickFirstHotelStep() {
        BaseMasterPage page = new BaseMasterPage() {};
        page.clickFirstHotel();
    }




}
