package com.web.base.utils.dbquery;


import com.web.base.backend.OracleConnection;
import com.web.base.backend.WebAutomationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBQueryUtil extends OracleConnection {
    private static final Log log = LogFactory.getLog(DBQueryUtil.class);
    private static DBQueryUtil instance;

    public static synchronized DBQueryUtil getInstance(){
        if (instance == null)
            instance = new DBQueryUtil();
        return instance;
    }
/*
    public List<String> getCodapayPendingPaymentDetails(String email, String channel){
        String query = "SELECT * FROM (  SELECT PI.BANK_GOVERNMENT_CODE, PI.BRANCH_CODE FROM dt_collection_dba.payment_instrument pi " +
            "WHERE bank_payment_source_code = '" + channel + "' and payment_instr_status_t_cd = 'ONAY_BEKLIYOR' and bill_account_id IN ( (SELECT PR.PARTY_ROLE_ACCOUNT_ID " +
            "FROM DT_PARTY_DBA.PARTY_ROLE_ACCOUNT pr WHERE pr.PARTY_ROLE_ACCOUNT_SPEC_CD = 'UYE_FATURA_HESABI' " +
            "AND PR.PARTY_ROLE_ID IN (SELECT C.PARTY_ROLE_ID FROM DT_LOGIN_DBA.USER_CREDENTIAL c WHERE c.credential_value = '" + email +
            "'))) ORDER BY 1 DESC) bgc WHERE ROWNUM = 1";
        Object[] result = executeSingleResultQuery(query);
        if(result == null){
            String error = "PAYMENT DATA NOT FOUND IN DB. QUERY IS: " + query;
            log.error(error);
            throw new WebAutomationException(error);
        }
        List<String> response = new ArrayList<String>();
        response.add((String)result[0]);
        response.add((String)result[1]);
        return response;
    }*/

    public String getSmsVerificationCode(String phoneNumber){
        String query = "select UC.VERIFICATION_KEY from DT_LOGIN_DBA.USER_CREDENTIAL uc where UC.CREDENTIAL_TYPE_CD = 'PHONE_NUMBER' " +
            "and UC.IS_REGISTER = 1 and UC.CREDENTIAL_VALUE = '" + phoneNumber + "'";
        String result = (String)executeSingleSimpleResultQuery(query, String.class);
        if(result == null){
            String error = "SMS VERIFICATION NOT FOUND ON DB. QUERY IS: " + query;
            log.error(error);
            throw new WebAutomationException(error);
        }
        return result;
    }

    public String getEmailPasswordResetLink(String email){
        String query = "select s.link from (SELECT SUBSTR (ARD.MODEL_VALUE, INSTR (ARD.MODEL_VALUE, 'https:'), " +
                        "INSTR (ARD.MODEL_VALUE, 'ONAY_KODU') - (INSTR (ARD.MODEL_VALUE, 'https:') + 3)) AS LINK, " +
                        "ARD.MODEL_VALUE,ARD.* FROM DT_POSTOFFICE_DBA.API_REQUEST_DETAIL ard, DT_LOGIN_DBA.USER_CREDENTIAL uc " +
                        "WHERE   UC.CREDENTIAL_VALUE = '" + email + "' and UC.PARTY_ROLE_ID = ARD.PARTY_ROLE_ID " +
                        "AND ARD.TEMPLATE_SPEC_CD = 'PASSWORD_RESET' " +
                        "ORDER BY ARD.CREATION_DATE DESC) s where rownum = 1";
        String result = (String)executeSingleSimpleResultQuery(query, String.class);
        if(result == null){
            String error = "EMAIL RESET LINK NOT FOUND ON DB. QUERY IS: " + query;
            log.error(error);
            throw new WebAutomationException(error);
        }
        return result;
    }

    public String getEmailVerificationLink(String email){
        String query = "select s.link from (SELECT SUBSTR (ARD.MODEL_VALUE, INSTR (ARD.MODEL_VALUE, 'https:'), " +
                "INSTR (ARD.MODEL_VALUE, 'ONAY_KODU') - (INSTR (ARD.MODEL_VALUE, 'https:') + 3)) AS LINK, " +
                "ARD.MODEL_VALUE,ARD.* FROM DT_POSTOFFICE_DBA.API_REQUEST_DETAIL ard, DT_LOGIN_DBA.USER_CREDENTIAL uc " +
                "WHERE UC.CREDENTIAL_VALUE = '" + email + "' and UC.PARTY_ROLE_ID = ARD.PARTY_ROLE_ID " +
                "AND ARD.TEMPLATE_SPEC_CD = 'ACTIVATION_MAIL' " +
                "ORDER BY communication_creation_date DESC) s where rownum = 1";
        String result = (String)executeSingleSimpleResultQuery(query, String.class);
        if(result == null){
            String error = "EMAIL VERIFICATION LINK NOT FOUND ON DB. QUERY IS: " + query;
            log.error(error);
            throw new WebAutomationException(error);
        }
        return result;
    }

    public String getSmsVerificationCodeForPasswordReset(String phoneNumber){
        String query = "select UL.RESET_KEY from DT_LOGIN_DBA.USER_CREDENTIAL uc,  DT_LOGIN_DBA.USER_LOGIN ul " +
                "where UC.USER_LOGIN_ID = UL.USER_LOGIN_ID and UC.CREDENTIAL_TYPE_CD = 'PHONE_NUMBER' " +
                "and UC.CREDENTIAL_VALUE = '" + phoneNumber + "' and UC.LOGICAL_DELETE_KEY is null " +
                "and UL.LOGICAL_DELETE_KEY is null";
        String result = (String)executeSingleSimpleResultQuery(query, String.class);
        if(result == null){
            String error = "SMS VERIFICATION NOT FOUND ON DB. QUERY IS: " + query;
            log.error(error);
            throw new WebAutomationException(error);
        }
        return result;
    }
/*
    public List<String> getMailTemplate(String email, String templateCode){
        String query = "select q1.MESSAGE_TEXT_RENDERED, PRA.ACCOUNT_NUMBER " +
                "from DT_PARTY_DBA.PARTY_ROLE_ACCOUNT pra " +
                "join (select ARD.MESSAGE_TEXT_RENDERED, ARD.PARTY_ROLE_ID " +
                "from DT_POSTOFFICE_DBA.API_REQUEST_DETAIL ard " +
                "where ARD.EMAIL = '" + email + "' " +
                "and ARD.TEMPLATE_SPEC_CD = '" + templateCode + "') q1 " +
                "on q1.PARTY_ROLE_ID = pra.PARTY_ROLE_ID " +
                "where account_number is not null and rownum = 1";
        Object[] result = executeSingleResultQuery(query);
        List<String> returnList = new ArrayList<String>();
        returnList.add((String)result[0]);
        returnList.add(((BigDecimal)result[1]).toString());
        return returnList;
    }*/
}
