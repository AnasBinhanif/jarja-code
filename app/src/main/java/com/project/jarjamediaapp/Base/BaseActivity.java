package com.project.jarjamediaapp.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.HashMap;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected IntentFilter intentFilter;
    protected EasyPreference.Builder easyPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        easyPreference = EasyPreference.with(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void setToolBarTitle(Toolbar toolbar, String title, boolean forActivity) {
        //getSupportActionBar().setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);


        if (forActivity) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do something you want
                    onBackPressed();
                }
            });
        }
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard();
                return false;
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void showLongToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void switchActivity(Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void switchActivityWithIntent(Class<? extends BaseActivity> activity, HashMap<String, Integer> map) {
        Intent intent = new Intent(this, activity);
        for (HashMap.Entry<String, Integer> entry : map.entrySet())
            intent.putExtra(entry.getKey(), entry.getValue());
        startActivity(intent);
    }

    public void switchActivityWithIntentString(Class<? extends BaseActivity> activity, HashMap<String, String> map) {
        Intent intent = new Intent(this, activity);
        for (HashMap.Entry<String, String> entry : map.entrySet())
            intent.putExtra(entry.getKey(), entry.getValue());
        startActivity(intent);
    }

    public class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }

    public void logout() {

     /*   easyPreference.remove(GH.KEYS.USER_ID.name())
                .remove(GH.KEYS.IS_LOGGED_IN.name())
                .save();*/


        //ToastUtils.showErrorToast(HomeActivity.this, "Session Expired", "Please Login Again");
        easyPreference.clearAll().save();
        switchActivity(LoginActivity.class);
        finishAffinity();
        //  startActivity(new Intent(this, LandingPageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    public boolean checkLocation(Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gps_enabled)
            return true;
        else
            return false;
    }

   /* public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return super.onCreateOptionsMenu(menu);
    }*/

}
