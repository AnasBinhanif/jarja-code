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
import java.util.Locale;

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
        AUTHORIZATION,
        AGENT_ID_CALENDAR


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

    public String getCalendarAgentId() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.AGENT_ID_CALENDAR.name(), "");
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
            formattedDate = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String formatTime(String dateString) {

        Date date;
        String formattedDate = "";
        try {

            // 2020-02-12T11:31:00
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String formatApiDateTime(String dateString) {

        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

    public String formatDateTime(String dateString) {

        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.sss'Z'", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat("MM-dd-yyyy h:mm a", Locale.getDefault()).format(date);
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

    public String formatter(String dateString, String newFormat, String oldFormat) {

        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat(oldFormat, Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat(newFormat, Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return formattedDate;
    }

}
