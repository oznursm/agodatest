package com.web.base.utils.dbquery;

import com.web.base.model.PhoneFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EmailPhoneNumberUtil {

    public static String generateEmail(String country){
        String environment = System.getenv("ENVIRONMENT").toLowerCase();
        String channel = "web";
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
        Date date = new Date();
        Random randomNumber = new Random();
        int num = randomNumber.nextInt(900000) + 100000;
        return environment + channel + formatter.format(date) + country.toLowerCase() + num + "@testinium.com";
    }

    public static String generatePhoneNumber(String country){
        PhoneFormat phoneFormat = AutomationDBQueryUtil.getInstance().getPhoneFormat(country);
        Random randomNumber = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(phoneFormat.getInitial());
        int i = 0;
        while(i++ < phoneFormat.getDigit()) {
            int num = randomNumber.nextInt(10);
            sb.append(num);
        }
        return String.valueOf(sb);
    }
}
