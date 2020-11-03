package com.project.jarjamediaapp.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.project.jarjamediaapp.R;

public class Loading extends ProgressDialog {

    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
    }

    public Loading(Context context) {
        super(context);

    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
