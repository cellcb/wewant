package me.cell.wewant.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class PriceUtil {

    public static String preproces(String price) {
        return price.replace("¥", "").replace("$", "");
    }

    public static BigDecimal convert2Number(String price) {
        String processed = preproces(price);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        BigDecimal parsedStringValue = null;
        try {
            parsedStringValue = (BigDecimal) decimalFormat.parse(processed);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return parsedStringValue;
    }

}
