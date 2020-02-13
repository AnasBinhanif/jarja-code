package com.project.jarjamediaapp.Base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.jarjamediaapp.Activities.login.LoginActivity;
import com.project.jarjamediaapp.Interfaces.UpdateTitle;
import com.project.jarjamediaapp.Utilities.EasyPreference;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseFragment extends Fragment {


    protected BaseActivity mActivity;
    protected String title = "";
    protected int position = 0;
    Bundle bundle;
    protected EasyPreference.Builder easyPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) this.getActivity();
        bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("title")) {
                title = bundle.getString("title", "");
            }
            if (bundle.containsKey("position")) {
                position = bundle.getInt("position", -1);
            }
        }
        easyPreference = EasyPreference.with(mActivity);
    }


    @Override
    public void onResume() {
        //((HomeActivity) mActivity).toolbar.setTitle(title);
        try {
            ((UpdateTitle) mActivity).updateToolBarTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //getActivity().setTitle("your title");
        //ForBaseFragment and MyPopLogic
        //((HomeActivity) mActivity).navigationView.setCheckedItem(position);
        //((HomeActivity) mActivity).navigationView.getMenu().getItem(position);
        super.onResume();
    }


    public boolean onFragmentBackPressed() {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void setupParent(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParent(innerView);
            }
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
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

    public boolean checkLocation(Context context) {

        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (manager != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    public void switchActivity(Context context, Class<? extends BaseActivity> activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

    public void switchActivityWithIntentString(Context context,Class<? extends BaseActivity> activity, HashMap<String, String> map) {
        Intent intent = new Intent(context, activity);
        for (HashMap.Entry<String, String> entry : map.entrySet())
            intent.putExtra(entry.getKey(), entry.getValue());
        startActivity(intent);
    }

    public void logout() {

     /*   easyPreference.remove(GH.KEYS.USER_ID.name())
                .remove(GH.KEYS.IS_LOGGED_IN.name())
                .save();*/


        //ToastUtils.showErrorToast(HomeActivity.this, "Session Expired", "Please Login Again");
        easyPreference.clearAll().save();
        switchActivity(getActivity(),LoginActivity.class);
        getActivity().finishAffinity();
        //  startActivity(new Intent(this, LandingPageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }


}
