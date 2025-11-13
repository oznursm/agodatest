package com.web.base.utils.dbquery;

import com.web.base.backend.BaseAutomationContext;
import com.web.base.backend.MysqlConnection;
import com.web.base.model.ApacVoucher;

public class VoucherUtil extends MysqlConnection {
    private static VoucherUtil instance;

    public static synchronized VoucherUtil getInstance(){
        if (instance == null)
            instance = new VoucherUtil();
        return instance;
    }

    public void getVoucherCode(String country){
        String query = "SELECT * FROM APAC_VOUCHER where country_code = '" + country + "' and environment = '" + System.getenv("ENVIRONMENT") + "' and is_used = 0 limit 1";
        Object[] result = executeSingleResultQuery(query);
        ApacVoucher apacVoucher = new ApacVoucher();
        apacVoucher.setId((Integer)result[0]);
        apacVoucher.setVoucherCode((String)result[1]);
        apacVoucher.setCountryCode((String)result[2]);
        apacVoucher.setEnvironment((String)result[3]);
        apacVoucher.setUsed((Integer)result[4] == 1);
        apacVoucher.setOfferType((String)result[5]);
        BaseAutomationContext.setApacVoucher(apacVoucher);
    }

    public void getPreviouslyUsedVoucherCode(String country){
        String query = "SELECT * FROM APAC_VOUCHER where country_code = '" + country + "' and environment = '" + System.getenv("ENVIRONMENT") + "' and is_used = 1 limit 1";
        Object[] result = executeSingleResultQuery(query);
        ApacVoucher apacVoucher = new ApacVoucher();
        apacVoucher.setId((Integer)result[0]);
        apacVoucher.setVoucherCode((String)result[1]);
        apacVoucher.setCountryCode((String)result[2]);
        apacVoucher.setEnvironment((String)result[3]);
        apacVoucher.setUsed((Integer)result[4] == 1);
        apacVoucher.setOfferType((String)result[5]);
        BaseAutomationContext.setApacVoucher(apacVoucher);
    }

    public void updateVoucherUsedStatus(){
        String updateQuery = "update APAC_VOUCHER set is_used = 1 where id = " + BaseAutomationContext.getApacVoucher().getId();
        executeSQLScript(updateQuery);
    }
}
