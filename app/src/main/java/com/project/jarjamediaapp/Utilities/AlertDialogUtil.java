package com.project.jarjamediaapp.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogUtil {

    MyDialogListener myDialogListener;
    private static final AlertDialogUtil ourInstance = new AlertDialogUtil();
    String alertType = "";

    public static AlertDialogUtil getInstance() {
        return ourInstance;
    }

    public void showDialog(Context context, String title, String message, int icon,
                           String positiveButtonText, String negativeButtonText,
                           boolean UI, final String alertType) {

        this.alertType = alertType;
        myDialogListener = (MyDialogListener) context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(icon);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        if (UI) {
            builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    myDialogListener.onPositiveButtonClicked(dialog, alertType);

                }
            });

            builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    myDialogListener.onNegativeButtonClicked(dialog, alertType);

                }
            });
        } else {
            builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    myDialogListener.onPositiveButtonClicked(dialog, alertType);

                }
            });
        }

        AlertDialog alert = builder.create();
        alert.show();

    }

    public interface MyDialogListener {

        void onPositiveButtonClicked(DialogInterface dialog, String alertType);

        void onNegativeButtonClicked(DialogInterface dialog, String alertType);

    }
}
