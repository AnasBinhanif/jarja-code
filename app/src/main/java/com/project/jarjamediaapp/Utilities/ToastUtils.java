
package com.project.jarjamediaapp.Utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Provides a simplified way to show toast messages without having to create the
 * toast, set the desired gravity, etc.
 */
public class ToastUtils {

    private static Toast toast;

    public enum Duration {
        SHORT, LONG
    }

    private ToastUtils() {
        throw new AssertionError();
    }

    public static void showToast(Context context, int stringResId) {
        showToast(context, stringResId, Duration.SHORT);
    }

    private static void showToast(Context context, int stringResId, Duration duration) {
        ShowToast(context, context.getString(stringResId), duration);
    }

    public static void showToast(Context context, String text) {
        ShowToast(context, text, Duration.SHORT);
    }

    public static void showToastLong(Context context, String text) {
        ShowToast(context, text, Duration.LONG);
    }

    public static void showToast(Context context, String text, Duration duration) {
        ShowToast(context, text, duration);
    }

    public static void showErrorToast(Activity activity, String message, String Message) {

        if (message.equals("") && !Message.equals("")) {

            Toast.makeText(activity, Message, Toast.LENGTH_SHORT).show();

        } else if (Message.equals("") && !message.equals("")) {

            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

        } else if (!Message.equals("") && !message.equals("")) {

            Toast.makeText(activity, message + "! " + Message, Toast.LENGTH_LONG).show();

        } else {
            // Message Need to be changed
            Toast.makeText(activity, "Unable to connect .Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    /*
   public static void ShowToast(Context context, String text, Duration duration) {
        Toast toast = Toast.makeText(context, text,
                (duration == Duration.SHORT ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG));
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    */

    private static void ShowToast(Context ctx, String message, Duration duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(ctx, message, (duration == Duration.SHORT ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG));
        //Toast toast = Toast.makeText(ctx, message, (duration == Duration.SHORT ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG));
        View view = toast.getView();
        TextView text = (TextView) view.findViewById(android.R.id.message);
        //Typeface typeface = Typeface.createFromAsset(ctx.getAssets(), "fonts/roboto_regular.ttf");
        //text.setTypeface(typeface);
        //text.setTextSize(12);
        toast.show();
    }

}
