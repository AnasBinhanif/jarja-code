package com.project.jarjamediaapp.Utilities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDialog;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.ProjectApplication;
import com.project.jarjamediaapp.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GH {

    private static final GH ourInstance = new GH();
    public KProgressHUD hud;
    AppCompatDialog progressDialog;

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
        AGENT_ID_CALENDAR,
        USER_NAME,
        USER_PERMISSIONS

    }

    public String getAuthorization() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.AUTHORIZATION.name(), null);
    }

    public GetUserPermission getUserPermissions() {
        Gson gson = new Gson();
        String jsonText = EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_PERMISSIONS.name(), null);;
        GetUserPermission text = gson.fromJson(jsonText, GetUserPermission.class);

        return text;

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

    public String getUserName() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_NAME.name(), "");
    }

    public void ShowProgressDialog(Context context) {

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
        hud = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();

    }

    public void HideProgressDialog() {

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }

    }

    /*public void ShowProgressDialog(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
        } else {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.show();

        }
        final ImageView img_loading_frame = progressDialog.findViewById(R.id.ivGifJM);
        Glide.with(activity).load(R.drawable.jm_loader).into(img_loading_frame);
    }*/

    /*public void HideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (final Exception e) {
            // Handle or log or ignore
            e.printStackTrace();
        } finally {
            this.progressDialog = null;
        }

    }*/

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
            formattedDate = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date);
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
            dateString = new SimpleDateFormat("MM-dd-yyyy h:mm a", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateString;
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
            dateString = new SimpleDateFormat(newFormat, Locale.getDefault()).format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dateString;
    }

    public void hideKeyboard(Context context, Activity activity) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}
