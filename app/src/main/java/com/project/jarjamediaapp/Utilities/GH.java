package com.project.jarjamediaapp.Utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.project.jarjamediaapp.ProjectApplication;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class GH {

    private static final GH ourInstance = new GH();
    public KProgressHUD hud;

    public static GH getInstance() {
        return ourInstance;
    }

    public GH() {
    }

    public enum KEYS {

        SOCKET_TOKEN,
        IS_LOGGED_IN,
        USER_ID,
        USER_FIRSTNAME,
        USER_LASTNAME,
        ACCESS_TOKEN,
        AUTHORIZATION


    }
    public String getAuthorization() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.AUTHORIZATION.name(), null);
    }

    public boolean isLoggedIn() {
        return EasyPreference.with(ProjectApplication.getInstance()).getBoolean(KEYS.IS_LOGGED_IN.name(), false);
    }

    public String getUserID() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_ID.name(), "");
    }

    public void ShowProgressDialog(Context context) {

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
        hud = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();

    }

    public void HideProgressDialog(Context context) {

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }

    }

    public String formatDate(String dateString) {

        Date date;
        String formattedDate = "";
        try {

            // 2020-02-12T11:31:00
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat("dd/MM/YYYY h:mm a", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String getMimeType(Context context, Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return extension;
    }

    public Map<String,Integer> getArrayReminder(){

        Map<String,Integer> arrayReminder= new HashMap<>();
        arrayReminder.put("None",0);
        arrayReminder.put("5 minutes",5);
        arrayReminder.put("10 minutes",10);
        arrayReminder.put( "15 minutes",15);
        arrayReminder.put("30 minutes", 30);
        arrayReminder.put("1 hour", 60);
        arrayReminder.put("2 hours", 120);
        arrayReminder.put("3 hours", 180);
        arrayReminder.put("3 hours", 180);
        arrayReminder.put("4 hours", 240);
        arrayReminder.put("5 hours", 300);
        arrayReminder.put("6 hours", 360);
        arrayReminder.put("7 hours", 420);
        arrayReminder.put("8 hours", 480);
        arrayReminder.put("9 hours", 540);
        arrayReminder.put("10 hours", 600);
        arrayReminder.put("11 hours", 660);
        arrayReminder.put("18 hours", 1080);
        arrayReminder.put("1 day", 1440);
        arrayReminder.put("2 days", 2880);
        arrayReminder.put("3 days", 4320);
        arrayReminder.put("4 days", 5760);
        arrayReminder.put("1 week", 10080);
        arrayReminder.put("2 weeks", 20160);


        return arrayReminder;

    }

}
