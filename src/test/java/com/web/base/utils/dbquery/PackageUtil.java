package com.web.base.utils.dbquery;

import com.web.base.backend.BaseAutomationContext;
import com.web.base.backend.ContextKeys;
import com.web.base.backend.MysqlConnection;
import com.web.base.backend.WebAutomationException;
import com.web.base.model.ApacPackageOffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PackageUtil {
    private static final Log log = LogFactory.getLog(PackageUtil.class);
    public static Date calculateExpireDate(String rule){
        Calendar cal = Calendar.getInstance();
        if(rule.startsWith("D")){
            cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(rule.substring(1)));
            return cal.getTime();
        } else if(rule.startsWith("C")){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(rule.substring(1));
            } catch (ParseException e) {
                throw new WebAutomationException(e.getMessage());
            }
        }
        return cal.getTime();
    }

    public static void getPackageOfferFromDB(String packageType, String offerType){
        String countryCode = BaseAutomationContext.getContextValue(ContextKeys.COUNTRY);
        String query = "select id, country_code, package_type, offer_type, package_order, offer_order, is_trial, price, trial_price, is_recurring, expire_rule from automation.APAC_PACKAGEOFFER where country_code = '"
                + countryCode + "' and package_type = '" + packageType + "' and offer_type = '" + offerType + "' and is_active = 1";
        Object[] result = MysqlConnection.getInstance().executeSingleResultQuery(query);
        if(result == null){
            String error = "NO CONFIG FOUND AT TABLE APAC_PACKAGEOFFER FOR PACKAGE TYPE: " + packageType + " OFFER TYPE: " + offerType;
            log.error(error);
            throw new WebAutomationException(error);
        }
        ApacPackageOffer apacPackageOffer = new ApacPackageOffer();
        apacPackageOffer.setId((Integer) result[0]);
        apacPackageOffer.setCountryCode((String) result[1]);
        apacPackageOffer.setPackageType((String) result[2]);
        apacPackageOffer.setOfferType((String) result[3]);
        apacPackageOffer.setPackageOrder((Integer) result[4]);
        apacPackageOffer.setOfferOrder((Integer) result[5]);
        apacPackageOffer.setTrial(((Integer) result[6]) == 1);
        apacPackageOffer.setPrice((String) result[7]);
        apacPackageOffer.setTrialPrice((String) result[8]);
        apacPackageOffer.setRecurring(((Integer) result[9]) == 1);
        apacPackageOffer.setExpireRule((String)result[10]);
        BaseAutomationContext.setApacPackageOffer(apacPackageOffer);
    }

}
