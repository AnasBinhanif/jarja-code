package com.project.jarjamediaapp.Utilities;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Validator instance = new Validator();
    private Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()+/{}';:~`,.?<>=_|])[A-Za-z\\d!@#$%^&*()+/{}';:~`,.?<>=_|]{8,}$";

    private static Validator getInstance() {

        return instance;
    }

    public boolean validate(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();

    }

    public String formatDateAustralian(String dateString, String originalPattern, String formattedPattern) {

        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat(originalPattern, Locale.getDefault()).parse(dateString);
            //  formattedDate = new SimpleDateFormat("dd MMMM yyyy, h:mm a", Locale.getDefault()).format(date);
            formattedDate = new SimpleDateFormat(formattedPattern, Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public String formatAmount(String amountCharged, String pattern) {

        String formattedAmount = "";
        try {
            DecimalFormat f = new DecimalFormat(pattern);
            double amount = Double.valueOf(amountCharged);
            formattedAmount = f.format(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedAmount;

    }

    public boolean isEmpty(TextView textView) {
        return textView.getText().toString().trim().length() == 0;
    }

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public boolean isInValidEmail(TextView textView) {
        return !(Patterns.EMAIL_ADDRESS.matcher(textView.getText().toString().trim()).matches());
    }

    public boolean isInValidEmail(EditText textView) {
        return !(Patterns.EMAIL_ADDRESS.matcher(textView.getText().toString().trim()).matches());
    }

    public double roundValue(double value, int places) {

        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public StringBuilder joinIds(StringBuilder stringBuilder, String data) {
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
        }
        stringBuilder.append(data);
        stringBuilder.append(",");
        //stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        return stringBuilder;
    }

}
