package com.project.jarjamediaapp.Firebase;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.splash.MainActivity;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;

import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public String TAG = "notifications";
    String title = "", message = "";
    EasyPreference.Builder easyPreference;
    Context context = MyFirebaseMessagingService.this;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.


        Log.i("remoteMessage",remoteMessage.getNotification().getBody());

      //  try {
            if (remoteMessage.getData().size() >  0) {

                title = remoteMessage.getData().get("title");
                message = remoteMessage.getData().get("body");
                String notificationType = remoteMessage.getData().get("notification_type");
                String notificationId = remoteMessage.getData().get("NotificationID");



                // Notification type for open activity
                /*case 1 : task
                * case 2 : futureTask
                * case 3 : followup
                * case 4 : appointment
                * */

                 switch (notificationType){
                     case "1":

                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,notificationType,notificationId);
                         } else {
                             sendNotification(title, message, HomeActivity.class,notificationType,notificationId);
                         }
                         break;
                     case "2":
                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,notificationType,notificationId);
                         } else {
                             sendNotification(title, message, HomeActivity.class,notificationType,notificationId);
                         }
                         break;
                     case "3":
                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,notificationType,notificationId);
                         } else {
                             sendNotification(title, message, HomeActivity.class,notificationType,notificationId);
                         }
                     case "4":

                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,notificationType,notificationId);
                         } else {
                             sendNotification(title, message, HomeActivity.class,notificationType,notificationId);
                         }
                 }


                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            }


//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    private void sendNotification(String title, String messageBody, Class<? extends AppCompatActivity> activity,String type,String notificationId) {

       /* easyPreference.addString(GH.KEYS.NOTIFICATIONTYPE.name(), type).save();
        easyPreference.addString(GH.KEYS.NOTIFICATIONID.name(), notificationId).save();*/
        Intent intent = new Intent(this, activity);
        intent.putExtra("notificationType",type);
        intent.putExtra("notificationID",notificationId);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_new)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotificationForOreo(String title, String messageBody, Class<? extends AppCompatActivity> activity, String type,String notificationId) {

       /* easyPreference.addString(GH.KEYS.NOTIFICATIONTYPE.name(), type).save();
        easyPreference.addString(GH.KEYS.NOTIFICATIONID.name(), notificationId).save();*/
        Intent intent = new Intent(this,activity);
        intent.putExtra("notificationType",type);
        intent.putExtra("notificationID",notificationId);
     //   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       /* intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      /*  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, HomeActivity.class), PendingIntent.FLAG_ONE_SHOT);*/

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int NOTIFICATION_ID = 234;
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String Description = "This is my channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        mChannel.setDescription(Description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mChannel.setShowBadge(false);
        notificationManager.createNotificationChannel(mChannel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_new)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    @Override
    public void onNewToken(String token) {

        easyPreference = EasyPreference.with(this);
        Log.d(TAG, "Refreshed token: " + token);
        easyPreference.addString(GH.KEYS.FIREBASE_TOEKN.name(), token + "").save();


        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }

   /* public boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }*/


}
