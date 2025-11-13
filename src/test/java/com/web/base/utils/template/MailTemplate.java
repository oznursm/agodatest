package com.web.base.utils.template;

import com.web.base.backend.BaseAutomationContext;
import com.web.base.backend.ContextKeys;
import com.web.base.backend.WebAutomationException;
import com.web.base.utils.dbquery.PackageUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MailTemplate {
    private static final Log log = LogFactory.getLog(MailTemplate.class);
    private static MailTemplate instance;

    public static synchronized MailTemplate getInstance(){
        if (instance == null)
            instance = new MailTemplate();
        return instance;
    }
/*
    public void checkEmailTemplate(String templateCode){
        log.info("ENTERING checkEmailTemplate");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            throw new WebAutomationException(e.getMessage());
        }
        List<String> templateAndAccount = DBQueryUtil.getInstance(DriverManagerType.CHROME).getMailTemplate(BaseAutomationContext.getContextValue(ContextKeys.EMAIL), templateCode);
        String template = templateAndAccount.get(0).replaceAll(" +"," ");
        String account = templateAndAccount.get(1);
        checkAccount(template, account);
        checkTitle(template, templateCode);
        switch (templateCode){
            case "ACTIVATION_MAIL":
                activationTemplate(template);
                break;
            case "WELCOME":
                welcomeTemplate(template);
                break;
            case "PRODUCT_SALES":
                productSalesTemplate(template);
                break;
            case "INVOICE_MAIL":
                invoiceTemplate(template);
                break;
            case "PASSWORD_RESET":
                resetPasswordTemplate(template);
                break;
            case "CONFIRMATION_MAIL_3007":
                cancellationTemplate(template);
                break;
        }
    }*/

    private void cancellationTemplate(String template){
        checkName(template);
        checkCancelText(template);
    }

    private void resetPasswordTemplate(String template){
        checkName(template);
        checkResetText(template);
    }

    private void activationTemplate(String template){
        checkName(template);
        checkContactMail(template);
    }

    private void welcomeTemplate(String template){
        checkWelcomeText(template);
    }

    private void productSalesTemplate(String template){
        checkName(template);
        checkContactMail(template);
        checkUserName(template);
        checkStartDate(template);
        checkEndDate(template);
    }

    private void invoiceTemplate(String template){
        checkName(template);
        checkContactMail(template);
        checkUserName(template);
        checkStartDate(template);
        checkEndDate(template);
    }

    private void checkAccount(String template, String account){
        String accountCheck = "Subscriber Id : " + account;
        if(!template.contains(accountCheck)){
            String error = "TEMPLATE DOES NOT CONTAIN SUBSCRIBE ID. EXPECTED IS: " + accountCheck;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE SUBSCRIBE ID CHECK SUCCESSFUL");
        }
    }

    private void checkName(String template){
        String nameCheck = BaseAutomationContext.getContextValue(ContextKeys.NAME) + " " + BaseAutomationContext.getContextValue(ContextKeys.SURNAME);
        if(!template.contains(nameCheck)){
            String error = "TEMPLATE DOES NOT CONTAIN NAME SURNAME. EXPECTED IS: " + nameCheck;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE NAME SURNAME CHECK SUCCESSFUL");
        }
    }

    private void checkTitle(String template, String templateCode){
        String titleCheck = getTitleByTemplateName(templateCode);
        if(!template.contains(titleCheck)){
            String error = "TEMPLATE DOES NOT CONTAIN TITLE. EXPECTED IS: " + titleCheck;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE TITLE CHECK SUCCESSFUL");
        }
    }

    private void checkContactMail(String template){
        String contactCheck = BaseAutomationContext.getContextValue(ContextKeys.COUNTRY).toLowerCase() + "email@email";
        if(!template.contains(contactCheck)){
            String error = "TEMPLATE DOES NOT CONTAIN CONTACT MAIL. EXPECTED IS: " + contactCheck;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE CONTACT MAIL CHECK SUCCESSFUL");
        }
    }

    private void checkWelcomeText(String template){
        String welcomeText = "<P> Thank you for registering with us. Now you can take advantage of the flexi <span style=\"color:#000000\"> package options to access matches from major European football leagues and other fantastic line-up of LIVE sports contents & more!<br> <br>";
        if(!template.contains(welcomeText)){
            String error = "TEMPLATE DOES NOT CONTAIN WELCOME TEXT. EXPECTED IS: " + welcomeText;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE WELCOME TEXT CHECK SUCCESSFUL");
        }
    }

    private void checkUserName(String template){
        String userText = "<P>Username: " + BaseAutomationContext.getContextValue(ContextKeys.EMAIL);
        if(!template.contains(userText)){
            String error = "TEMPLATE DOES NOT CONTAIN USER TEXT. EXPECTED IS: " + userText;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE USER TEXT CHECK SUCCESSFUL");
        }
    }

    private void checkResetText(String template){
        String resetText = "Your password reset request for CONNECT account has been received";
        if(!template.contains(resetText)){
            String error = "TEMPLATE DOES NOT CONTAIN RESET TEXT. EXPECTED IS: " + resetText;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE RESET TEXT CHECK SUCCESSFUL");
        }
    }

    private void checkCancelText(String template){
        String cancelText = "This email is to confirm that you have cancelled your subscription to ";
        if(!template.contains(cancelText)){
            String error = "TEMPLATE DOES NOT CONTAIN CANCEL TEXT. EXPECTED IS: " + cancelText;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE CANCEL TEXT CHECK SUCCESSFUL");
        }
    }

    private void checkStartDate(String template){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM.yyyy", Locale.US);
        Date today = Calendar.getInstance().getTime();
        String startDate = sdf.format(today);
        if(!template.contains(startDate)){
            String error = "TEMPLATE DOES NOT CONTAIN START DATE. EXPECTED IS: " + startDate;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE START DATE CHECK SUCCESSFUL");
        }
    }

    private void checkEndDate(String template){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MMM.yyyy", Locale.US);
        Date expire = PackageUtil.calculateExpireDate(BaseAutomationContext.getApacPackageOffer().getExpireRule());
        String endDate = sdf.format(expire);
        if(!template.contains(endDate)){
            String error = "TEMPLATE DOES NOT CONTAIN END DATE. EXPECTED IS: " + endDate;
            log.info(template);
            log.error(error);
            throw new WebAutomationException(error);
        } else {
            log.info("TEMPLATE END DATE CHECK SUCCESSFUL");
        }
    }

    private String getTitleByTemplateName(String templateName){
        switch (templateName){
            case "ACTIVATION_MAIL":
                return "Email Verification";
            case "BASE_WELCOME":
                return "Welcome CONNECT!";
            case "PRODUCT_SALES":
                return "Thank You For Subscription!";
            case "INVOICE_MAIL":
                return "Invoice";
            case "PASSWORD_RESET":
                return "Password Reset";
            case "CONFIRMATION_MAIL_3007":
                return "Subscription Cancellation";
            default: return "";
        }
    }
}
