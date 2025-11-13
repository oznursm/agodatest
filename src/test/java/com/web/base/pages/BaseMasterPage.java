package com.web.base.pages;

import com.web.base.backend.BaseAutomationContext;
import com.web.base.backend.ContextKeys;
import com.web.base.backend.WebAutomationException;
import com.web.base.pageElement.BeinButton;
import com.web.base.pageElement.BeinTextBox;
import com.web.base.pageElement.PageElementModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class BaseMasterPage {
    private static final Log log = LogFactory.getLog(BaseMasterPage.class);
    private static BeinButton BTN_AccountProfile = new BeinButton(PageElementModel.selectorNames.CLASS_NAME,"member-name");
    private static BeinButton BTN_WatchLive = new BeinButton(PageElementModel.selectorNames.CLASS_NAME, "idx-livetv");
    private static BeinButton BTN_Logout = new BeinButton(PageElementModel.selectorNames.XPATH, "//a[contains(@href,'logout')]");
    private static BeinButton BTN_Subscription = new BeinButton(PageElementModel.selectorNames.XPATH, "//a[contains(@href,'purchases')]");
    private static BeinButton BTN_Profile = new BeinButton(PageElementModel.selectorNames.XPATH, "//a[contains(@href,'profile')]");
    private static BeinButton BTN_Voucher = new BeinButton(PageElementModel.selectorNames.XPATH, "//a[contains(@href,'couponcode')]");
    private static BeinButton BTN_Mailbox = new BeinButton(PageElementModel.selectorNames.XPATH, "//a[contains(@href,'case')]");
    private static BeinButton BTN_ClickPopUp = new BeinButton(PageElementModel.selectorNames.cssSelector,"button[aria-label='Close']");
    private static BeinTextBox TXT_SearchBox = new BeinTextBox(PageElementModel.selectorNames.ID, "textInput");
    private static BeinButton BTN_FirstSuggestion =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "li[data-selenium='autosuggest-item']");
    private static BeinButton BTN_NextMonth =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "button[data-selenium='calendar-next-month-button']");
    private static BeinButton BTN_July1_2026 =
            new BeinButton(PageElementModel.selectorNames.XPATH,
                    "//div[@role='button' and contains(@aria-label,'Jul 01 2026')]");
    private static BeinButton BTN_July6_2026 =
            new BeinButton(PageElementModel.selectorNames.XPATH,
                    "//div[@role='button' and contains(@aria-label,'Jul 06 2026')]");
    private static BeinButton BTN_AddChild =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "button[aria-label='Ekle Çocuk']");
    // Çocuk yaş dropdown'unu açan buton (öncekiyle aynı)
    private static BeinButton BTN_ChildAgeDropdown =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "button[data-element-name='occ-child-age-dropdown']");

    // Açılan listeden 6 yaş seçeneği
    private static BeinButton BTN_ChildAge6 =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "li[data-testid='child-ages-dropdown-0-6']");

    private static BeinButton BTN_Search =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "button[data-selenium='searchButton']");
    // ⭐ 5 yıldız
    private static BeinButton CHK_5Star =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "label[data-element-name='search-filter-starratingwithluxury'][data-element-value='5']");

    // 👍 Değerlendirme: İyi
    private static BeinButton CHK_Review_Good =
            new BeinButton(PageElementModel.selectorNames.XPATH,
                    "//span[@data-selenium='filter-item-text' and normalize-space()='İyi']/ancestor::label[1]");

    // 🏊‍♂️ Tesis olanakları: Yüzme havuzu
    private static BeinButton CHK_Facility_Pool =
            new BeinButton(PageElementModel.selectorNames.XPATH,
                    "//span[@data-selenium='filter-item-text' and contains(normalize-space(),'Yüzme havuzu')]/ancestor::label[1]");

    // 🍳 Oda teklifleri: Kahvaltı dahil
    private static BeinButton CHK_Offer_Breakfast =
            new BeinButton(PageElementModel.selectorNames.XPATH,
                    "//span[@data-selenium='filter-item-text' and normalize-space()='Kahvaltı dahil']/ancestor::label[1]");

    // 📍 Bölge: Antalya şehir merkezi
   /* private static BeinButton CHK_Area_AntalyaCenter =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "label[data-element-name='search-filter-hotelareaid'][data-element-value='17779']");

    // 👨‍👩‍👧‍👦 Tesis olanakları: Aile/ çocuklar için uygun
    private static BeinButton CHK_Facility_FamilyFriendly =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "label[data-element-name='search-filter-hotelfacilities'][data-element-value='89']");
*/

    private static BeinButton BTN_FirstHotel =
            new BeinButton(PageElementModel.selectorNames.cssSelector,
                    "a[data-element-name='property-card-content']");


    public static String Search_Keyword = System.getenv("SEARCH_KEYWORD");
    public void logout(){
        log.info("ENTERING logout");
        BTN_AccountProfile.waitUntilVisibleAndClick();
        BTN_Logout.waitUntilVisibleAndClick();
        log.info("USED E-MAIL IS: " + BaseAutomationContext.getContextValue(ContextKeys.EMAIL));
    }

    public boolean checkLoggedIn(){
        log.info("ENTERING checkLoggedIn");
        return BTN_AccountProfile.isDisplayed();
    }

    public void subscription(){
        log.info("ENTERING subscription");
        BTN_AccountProfile.waitUntilVisibleAndClick();
        BTN_Subscription.waitUntilVisibleAndClick();
    }

    public void clickMailbox() {
        log.info("ENTERING clickMailbox");
        BTN_AccountProfile.waitUntilVisibleAndClick();
        BTN_Mailbox.waitUntilVisibleAndClick();

    }

    public void profile(){
        log.info("ENTERING subscription");
        BTN_AccountProfile.waitUntilVisibleAndClick();
        BTN_Profile.waitUntilVisibleAndClick();
    }

    public void checkNonExistenceOfSubscription(){
        log.info("ENTERING checkNonExistenceOfSubscription");
        BTN_AccountProfile.waitUntilVisibleAndClick();
        if(BTN_Subscription.isDisplayed()){
            String error = "SUBSCRIBE BUTTON SHOULD NOT BE DISPLAYED FOR PARTNER LOGIN";
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("CHECK NON EXISTENCE OF SUBSCRIPTION IS SUCCESSFUL");
        }
    }

    public void checkNonExistenceOfVoucher(){
        log.info("ENTERING checkNonExistenceOfSubscription");
        BTN_AccountProfile.waitUntilVisibleAndClick();
        if(BTN_Voucher.isDisplayed()){
            String error = "VOUCHER BUTTON SHOULD NOT BE DISPLAYED FOR PARTNER LOGIN";
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("CHECK NON EXISTENCE OF VOUCHER IS SUCCESSFUL");
        }
    }

    public void clickWatchLive() {
        log.info("ENTERING clickWatchLive");
        BTN_WatchLive.waitFor(5);
        BTN_WatchLive.waitUntilVisible();
        BTN_WatchLive.clickAndWait();
    }

    public void ClickPopUp(){
        BTN_ClickPopUp.click();
    }
    public void typeSearchKeyword() {
        TXT_SearchBox.clearText();
        TXT_SearchBox.waitUntilVisibleAndType(Search_Keyword);
        TXT_SearchBox.pressEnter();
    }
    public void clickFirstAntalyaSuggestion() {
        BTN_FirstSuggestion.waitUntilVisibleAndClick();
    }
    public void selectDateRange_1to6_July2026() {
        // Maksimum 24 ay ileri dene (güvenlik payı)
        int maxClick = 24;

        while (maxClick-- > 0) {
            if (BTN_July1_2026.isDisplayed()) {
                BTN_July1_2026.waitUntilVisibleAndClick();  // başlangıç tarihi
                BTN_July6_2026.waitUntilVisibleAndClick();  // bitiş tarihi
                return;
            } else {
                BTN_NextMonth.clickAndWait(1); // 1 sn bekleyerek ayı ilerlet
            }
        }

        throw new WebAutomationException("July 2026 calendar not found after clicking Next Month.");
    }
    public void increaseRoomOccupancy() {
        // Çocuğu 0 → 1 yap
        BTN_AddChild.waitUntilVisibleAndClick();
    }
    public void selectChildAge6() {
        // Dropdown'u aç
        BTN_ChildAgeDropdown.waitUntilVisibleAndClick();

        // 6 yaşı seç
        BTN_ChildAge6.waitUntilVisibleAndClick();
    }


    public void clickSearchButton() {
        BTN_Search.waitUntilVisibleAndClick();
    }
    public void applyAllHotelFilters() {
        // 1) 5 yıldız
        CHK_5Star.waitUntilVisibleAndClick();

        // 2) Değerlendirme: İyi
        CHK_Review_Good.waitUntilVisibleAndClick();

        // 3) Tesis olanakları: Yüzme havuzu
        CHK_Facility_Pool.waitUntilVisibleAndClick();

        // 4) Oda teklifleri: Kahvaltı dahil
        CHK_Offer_Breakfast.waitUntilVisibleAndClick();

       /* // 5) Bölge: Antalya şehir merkezi
        CHK_Area_AntalyaCenter.waitUntilVisibleAndClick();

        // 6) Tesis olanakları: Aile/ çocuklar için uygun
        CHK_Facility_FamilyFriendly.waitUntilVisibleAndClick();*/
        try {
            Thread.sleep(3000); // 3 saniye
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void clickFirstHotel() {
        BTN_FirstHotel.waitUntilVisibleAndClick();

        try {
            Thread.sleep(3000); // 3 saniye
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
