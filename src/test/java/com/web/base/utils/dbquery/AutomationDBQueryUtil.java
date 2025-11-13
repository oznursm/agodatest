package com.web.base.utils.dbquery;


import com.web.base.backend.BaseAutomationContext;
import com.web.base.backend.ContextKeys;
import com.web.base.backend.MysqlConnection;
import com.web.base.backend.WebAutomationException;
import com.web.base.model.ApacSubscription;
import com.web.base.model.PhoneFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AutomationDBQueryUtil extends MysqlConnection {

    private static AutomationDBQueryUtil instance;
    private static final Log log = LogFactory.getLog(AutomationDBQueryUtil.class);

    public static synchronized AutomationDBQueryUtil getInstance(){
        if (instance == null)
            instance = new AutomationDBQueryUtil();
        return instance;
    }

    public String getPaymentTranslation(String countryCode, String paymentCode){
        String query = "select payment_id from automation.APAC_PAYMENT where payment_code = '" + paymentCode + "' and country_code = '" + countryCode +
                "' and environment='" + System.getenv("ENVIRONMENT") + "'";
        return (String)executeSingleSimpleResultQuery(query, String.class);
    }

    public void insertSubscription(ApacSubscription apacSubscription){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String query = "insert into automation.APAC_SUBSCRIPTION(environment, country_code, offer_type, email, password, package_expire, is_cancellable) values('" +
                System.getenv("ENVIRONMENT") + "','" + apacSubscription.getCountryCode() + "','" + apacSubscription.getOfferType() + "','" + apacSubscription.getEmail() +
                "','" + apacSubscription.getPassword() + "','" + sdf.format(apacSubscription.getExpire()) + "'," + apacSubscription.getCancellable().compareTo(false) + ")";
        executeSQLScript(query);
    }

    public void findCancellable(String countryCode, String offerType){
        String query = "select * from automation.APAC_SUBSCRIPTION where environment = '" + System.getenv("ENVIRONMENT") +
                "' and country_code = '" + countryCode + "' and offer_type = '" + offerType + "' and is_cancellable = 1 and is_cancelled = 0 order by id desc limit 1";
        Object[] result = executeSingleResultQuery(query);
        ApacSubscription apacSubscription = new ApacSubscription();
        apacSubscription.setId((int)result[0]);
        apacSubscription.setEnvironment((String)result[1]);
        apacSubscription.setCountryCode((String)result[2]);
        apacSubscription.setOfferType((String)result[3]);
        apacSubscription.setEmail((String)result[4]);
        apacSubscription.setPassword((String)result[5]);
        apacSubscription.setExpire((Date) result[6]);
        apacSubscription.setCancellable((Boolean) result[7]);
        apacSubscription.setCancelled((Boolean) result[8]);
        BaseAutomationContext.setApacSubscription(apacSubscription);
    }

    public void findEmailByCountry(String countryCode){
        String query = "select * from automation.APAC_SUBSCRIPTION where environment = '" + System.getenv("ENVIRONMENT") +
                "' and country_code = '" + countryCode + "' and email like '%@%' and is_cancelled = 0 order by id desc limit 1";
        Object[] result = executeSingleResultQuery(query);
        ApacSubscription apacSubscription = new ApacSubscription();
        apacSubscription.setId((int)result[0]);
        apacSubscription.setEnvironment((String)result[1]);
        apacSubscription.setCountryCode((String)result[2]);
        apacSubscription.setOfferType((String)result[3]);
        apacSubscription.setEmail((String)result[4]);
        apacSubscription.setPassword((String)result[5]);
        apacSubscription.setExpire((Date) result[6]);
        apacSubscription.setCancellable((Boolean) result[7]);
        apacSubscription.setCancelled((Boolean) result[8]);
        BaseAutomationContext.setApacSubscription(apacSubscription);
        BaseAutomationContext.addContext(ContextKeys.EMAIL, (String)result[4]);
    }

    public void findPhoneByCountry(String countryCode){
        String query = "select * from automation.APAC_SUBSCRIPTION where environment = '" + System.getenv("ENVIRONMENT") +
                "' and country_code = '" + countryCode + "' and email not like '%@%' and is_cancelled = 0 order by id desc limit 1";
        Object[] result = executeSingleResultQuery(query);
        ApacSubscription apacSubscription = new ApacSubscription();
        apacSubscription.setId((int)result[0]);
        apacSubscription.setEnvironment((String)result[1]);
        apacSubscription.setCountryCode((String)result[2]);
        apacSubscription.setOfferType((String)result[3]);
        apacSubscription.setEmail((String)result[4]);
        apacSubscription.setPassword((String)result[5]);
        apacSubscription.setExpire((Date) result[6]);
        apacSubscription.setCancellable((Boolean) result[7]);
        apacSubscription.setCancelled((Boolean) result[8]);
        BaseAutomationContext.setApacSubscription(apacSubscription);
    }

    public void findCancelled(String countryCode, String offerType){
        String query = "select * from automation.APAC_SUBSCRIPTION where environment = '" + System.getenv("ENVIRONMENT") +
                "' and country_code = '" + countryCode + "' and offer_type = '" + offerType + "' and is_cancellable = 1 and is_cancelled = 1 order by id desc limit 1";
        Object[] result = executeSingleResultQuery(query);

        if(result == null){
            String error = "NO CANCELLED SUBSCRIPTION FOUND FOR COUNTRY: " + countryCode + " OFFER TYPE: " + offerType;
            log.error(error);
            throw new WebAutomationException(error);
        }
        ApacSubscription apacSubscription = new ApacSubscription();
        apacSubscription.setId((int)result[0]);
        apacSubscription.setEnvironment((String)result[1]);
        apacSubscription.setCountryCode((String)result[2]);
        apacSubscription.setOfferType((String)result[3]);
        apacSubscription.setEmail((String)result[4]);
        apacSubscription.setPassword((String)result[5]);
        apacSubscription.setExpire((Date) result[6]);
        apacSubscription.setCancellable((Boolean) result[7]);
        apacSubscription.setCancelled((Boolean) result[8]);
        BaseAutomationContext.setApacSubscription(apacSubscription);
    }

    public void findPasswordNotChanged(String countryCode){
        String query = "select * from automation.APAC_SUBSCRIPTION where environment = '" + System.getenv("ENVIRONMENT") +
                "' and country_code = '" + countryCode + "' and is_password_changed = 0 order by id desc limit 1";
        Object[] result = executeSingleResultQuery(query);

        if(result == null){
            String error = "NO PASSWORD NOT CHANGED SUBSCRIPTION FOUND FOR COUNTRY: " + countryCode;
            log.error(error);
            throw new WebAutomationException(error);
        }
        ApacSubscription apacSubscription = new ApacSubscription();
        apacSubscription.setId((int)result[0]);
        apacSubscription.setEnvironment((String)result[1]);
        apacSubscription.setCountryCode((String)result[2]);
        apacSubscription.setOfferType((String)result[3]);
        apacSubscription.setEmail((String)result[4]);
        apacSubscription.setPassword((String)result[5]);
        apacSubscription.setExpire((Date) result[6]);
        apacSubscription.setCancellable((Boolean) result[7]);
        apacSubscription.setCancelled((Boolean) result[8]);
        apacSubscription.setPasswordChanged((Boolean) result[9]);
        BaseAutomationContext.setApacSubscription(apacSubscription);
    }

    public void updatePasswordChangedStatus(int id, String passwordChanged, String newPassword){
        String query = "update automation.APAC_SUBSCRIPTION set is_password_changed = " + passwordChanged + ", password = '" + newPassword + "' where id = " + id;
        executeSQLScript(query);
    }

    public void updateCancelledStatus(int id, String cancelStatus){
        String query = "update automation.APAC_SUBSCRIPTION set is_cancelled = " + cancelStatus + " where id = " + id;
        executeSQLScript(query);
    }

    public PhoneFormat getPhoneFormat(String countryCode){
        String query = "SELECT initial, digit FROM automation.APAC_PHONEFORMAT where country_code = '" + countryCode + "'";
        Object[] result = executeSingleResultQuery(query);
        if(result == null){
            String error = "PHONE FORMAT DATA NOT FOUND IN DB. QUERY IS: " + query;
            log.error(error);
            throw new WebAutomationException(error);
        }
        PhoneFormat phoneFormat = new PhoneFormat();
        phoneFormat.setDigit((Integer)result[1]);
        phoneFormat.setInitial((String)result[0]);
        return phoneFormat;
    }

    public String getRandomCreditCardNumber(String countryCode){
        String query = "SELECT id, card_number FROM automation.APAC_CREDITCARD where region = '" + countryCode + "' and environment = '" + System.getenv("ENVIRONMENT") + "' and is_used = 0 limit 1";
        Object[] result = executeSingleResultQuery(query);
        int id = (int)result[0];
        query = "UPDATE automation.APAC_CREDITCARD set is_used = 1 where id = " + id;
        executeSQLScript(query);
        return (String)result[1];
    }
}
