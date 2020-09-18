package com.project.jarjamediaapp.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Activities.user_profile.GetPermissionModel;
import com.project.jarjamediaapp.Models.GetUserPermission;
import com.project.jarjamediaapp.ProjectApplication;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.ActivityChangePasswordBinding;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GH {

    private static final GH ourInstance = new GH();
    public KProgressHUD hud;
    ProgressDialog progressDialog;

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
        AGENT_ID_INT,
        AGENT_NAME,
        USER_NAME,
        USER_PERMISSIONS,
        FIREBASE_TOEKN,
        NOTIFICATIONTYPE,
        NOTIFICATIONID,
        FRAGMENTSTATUS,
        CALENDERUPDATELIST,
        NAVIGATIONSTATUS,
        ISNOTIFICATIONALLOW,
        USER_PERMISSIONS_SETTINGS,
        USER_PERMISSIONS_DASHBOARD,
        USER_PERMISSIONS_LEAD,
        USER_PERMISSIONS_CALENDER

    }

    public String getAuthorization() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.AUTHORIZATION.name(), null);

    }

    public String getUserPermissonProfile() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_PERMISSIONS_SETTINGS.name(), null);

    }
    public String getUserPermissonDashboard() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_PERMISSIONS_DASHBOARD.name(), null);

    }
    public String getUserPermissonLead() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_PERMISSIONS_LEAD.name(), null);

    }
    public String getUserPermissonCalender() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_PERMISSIONS_CALENDER.name(), null);

    }

    public String getNotificationType() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.NOTIFICATIONTYPE.name(), "false");

    }
    public int getCalenderListPos() {
        return EasyPreference.with(ProjectApplication.getInstance()).getInt(KEYS.CALENDERUPDATELIST.name(), 0);

    }

    public String getFragmentStatus() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.FRAGMENTSTATUS.name(), null);

    }
    public String getNotificationID() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.NOTIFICATIONID.name(), null);

    }
    public String getNotificationAllowStatus() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.ISNOTIFICATIONALLOW.name(), "false");

    }
    public void addNotificationType(String notificationType){

        EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.NOTIFICATIONTYPE.name(), notificationType);
    }


    public GetPermissionModel getUserPermissions() {
        Gson gson = new Gson();
        String jsonText = EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_PERMISSIONS.name(), null);
        ;
        GetPermissionModel text = gson.fromJson(jsonText, GetPermissionModel.class);

        return text;

    }

    public boolean isLoggedIn() {
        return EasyPreference.with(ProjectApplication.getInstance()).getBoolean(KEYS.IS_LOGGED_IN.name(), false);
    }

    public String getCalendarAgentId() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.AGENT_ID_CALENDAR.name(), "");
    }

    public String getAgentName() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.AGENT_NAME.name(), "");
    }


    public int getAgentID() {
        return EasyPreference.with(ProjectApplication.getInstance()).getInt(KEYS.AGENT_ID_INT.name(), 0);
    }


    public String getUserName() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_NAME.name(), "");
    }

    public String getUserID() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.USER_ID.name(), "");
    }

    public String getFirebaseToken() {
        return EasyPreference.with(ProjectApplication.getInstance()).getString(KEYS.FIREBASE_TOEKN.name(), "");
    }

    public boolean getDashboardNavigationStatus() {
        return EasyPreference.with(ProjectApplication.getInstance()).getBoolean(KEYS.NAVIGATIONSTATUS.name(), false);
    }


  /*  public void ShowProgressDialog(Context context) {

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
        hud = KProgressHUD.create(context)
                .setCancellable(false)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).show();

    }

    public void HideProgressDialog() {

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }

    }*/

    public void ShowProgressDialog(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
        } else {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Please Wait....");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
            });


        }
       /* final ImageView img_loading_frame = progressDialog.findViewById(R.id.ivGifJM);
        Glide.with(activity).load(R.drawable.jm_loader).into(img_loading_frame);*/
    }

    public void HideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
             //  Looper.myLooper().quit();
            }
        } catch (final Exception e) {
            // Handle or log or ignore
            e.printStackTrace();
        } finally {
            this.progressDialog = null;
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

    public String getCurrentDate() {

        String formattedDate = "";
        try {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
            formattedDate = df.format(c);
        } catch (Exception e) {
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
    public void discardChangesDailog(Context context) {

        AlertDialog alertDialog1;
        alertDialog1 = new AlertDialog.Builder(
                context).create();

        // Setting Dialog Title
        alertDialog1.setTitle("You have made some changes.");

        // Setting Dialog Message
        alertDialog1.setMessage(context.getResources().getString(R.string.discard_dialog_text));
        alertDialog1.setCanceledOnTouchOutside(false);

        // Setting Icon to Dialog
        // alertDialog1.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog1.setButton("Discard", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                alertDialog1.dismiss();
                ((Activity)context).finish();

            }
        });

        alertDialog1.setButton2("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                alertDialog1.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog1.show();
    }


}
