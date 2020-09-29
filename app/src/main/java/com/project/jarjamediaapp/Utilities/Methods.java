package com.project.jarjamediaapp.Utilities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Patterns;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.jarjamediaapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {

    public static boolean isEmpty(TextView textView) {
        return textView.getText().toString().trim().length() == 0;
    }

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public static boolean isEmpty(MaterialSpinner editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public static boolean isInValidEmail(TextView textView) {
        return !(Patterns.EMAIL_ADDRESS.matcher(textView.getText().toString().trim()).matches());
    }

    public static boolean isInValidEmail(EditText textView) {
        return !(Patterns.EMAIL_ADDRESS.matcher(textView.getText().toString().trim()).matches());
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()+/{}';:~`,.?<>=_|])[A-Za-z\\d!@#$%^&*()+/{}';:~`,.?<>=_|]{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static String formatDate(String dateString) {
        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat("EEEE, h:mm a", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static float distance(float lat_a, float lng_a, float lat_b, float lng_b) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }


    public void DateDialogProfileLearner(final Context context, final TextView tv, boolean disablePastDates, boolean disableFutureDates) {

        try {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                tv.setTag(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
//                tv.setText( dayOfMonth + "-" + (monthOfYear + 1)   + "-" + year);
                    String myday = dayOfMonth > 9 ? "" + dayOfMonth : ("0" + dayOfMonth);
                    int selectedMonth = (monthOfYear + 1);
                    String mymonth = selectedMonth > 9 ? "" + selectedMonth : ("0" + selectedMonth);

                    tv.setText(myday + "/" + mymonth + "/" + year);
                    tv.setTag(myday + "/" + mymonth + "/" + year);

                    Date currentDate = new Date();
                    int age = Calendar.getInstance().get(Calendar.YEAR) - year;


                }
            };
            final DatePickerDialog dpDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            if (disablePastDates)
                dpDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            if (disableFutureDates)
                dpDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dpDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    dpDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorBlack));
                    dpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
            });
            dpDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
