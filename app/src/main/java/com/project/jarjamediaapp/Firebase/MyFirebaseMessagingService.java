package com.project.jarjamediaapp.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.project.jarjamediaapp.Activities.HomeActivity;
import com.project.jarjamediaapp.Activities.notification.NotificationActivity;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.EasyPreference;
import com.project.jarjamediaapp.Utilities.GH;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public String TAG = "notifications";
    String title = "", message = "";
    EasyPreference.Builder easyPreference;
    Context context = MyFirebaseMessagingService.this;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.


        Log.i("remoteMessage",remoteMessage.toString());

        try {
            if (remoteMessage.getData().size() >  0) {

                title = remoteMessage.getData().get("title");
                message = remoteMessage.getData().get("body");
                String notificationType = remoteMessage.getData().get("notification_type");

                // Notification type for open activity
                /*case 1 : task
                * case 2 : futureTask
                * case 3 : followup
                * case 4 : appointment
                * */

                 switch (notificationType){
                     case "1":

                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,"task");
                         } else {
                             sendNotification(title, message, HomeActivity.class,"task");
                         }
                         break;
                     case "2":
                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,"futureTask");
                         } else {
                             sendNotification(title, message, HomeActivity.class,"futureTask");
                         }
                         break;
                     case "3":
                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,"followup");
                         } else {
                             sendNotification(title, message, HomeActivity.class,"followup");
                         }
                     case "4":

                         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                             sendNotificationForOreo(title, message, HomeActivity.class,"apointment");
                         } else {
                             sendNotification(title, message, HomeActivity.class,"apointment");
                         }
                 }


               /* title =  remoteMessage.getNotification().getTitle();//remoteMessage.getNotification().getTitle();//remoteMessage.getData().get("title");
                message = "hello";remoteMessage.getData().get("body");*/

               // not used in anywhere so comment this code
              //  if (GH.getInstance().isLoggedIn()) {

                 /*   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        sendNotificationForOreo(title, message, HomeActivity.class);
                    } else {
                        sendNotification(title, message, HomeActivity.class);
                   }*/

         //       }
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            }/*else if(remoteMessage.getData().size() > 0){

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    sendNotificationForOreo(title, message, HomeActivity.class);
                } else {
                    sendNotification(title, message, HomeActivity.class);
                }
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendNotification(String title, String messageBody, Class<? extends AppCompatActivity> activity,String type) {

        Intent intent = new Intent(this, activity);
        intent.putExtra("notificationType",type);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

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
    private void sendNotificationForOreo(String title, String messageBody, Class<? extends AppCompatActivity> activity, String type) {

        Intent intent = new Intent(this, activity);
        intent.putExtra("notificationType",type);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

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


}
