package com.project.jarjamediaapp.Utilities;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.project.jarjamediaapp.ProjectApplication;

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

}
