package com.example.windowsv8.e_voting.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.windowsv8.e_voting.R;
import com.example.windowsv8.e_voting.activity.pemilih.MainActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    SharedPreferences sharedPreferences;
    Boolean session = false;
    public static final String shared_preference_user = "shared_preference_user";
    public static final String TAG_SESSION = "session_status";
    public final static String TAG_NPM = "npm";
    public final static String TAG_NAME = "name";
    public final static String TAG_IMAGE = "image";
    public final static String TAG_PROFILE = "profile";
    public final static String TAG_VERIF = "verifikasi";
    public final static String TAG_PILIH = "pilih";

    int notificationId = 1;
    int importance = NotificationManager.IMPORTANCE_HIGH;
    Long a[] = {
            1000L,
            1000L,
            1000L,
            1000L,
    };

//    @Override
//    public void onNewToken(String s) {
//        super.onNewToken(s);
//
//        sharedPreferences = getSharedPreferences(shared_preference_user, Context.MODE_PRIVATE);
//        session = sharedPreferences.getBoolean(TAG_SESSION, false);
//        Log.e("sessionfcm", session.toString());
//
//        if (session){
//            if (s.equals(sharedPreferences.getString(TAG_NPM, null))){
//                updateUserToken(s);
//            }
//        }
//    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationcompat = new NotificationCompat.Builder(this).setWhen(System.currentTimeMillis());
        notificationcompat.setContentTitle(remoteMessage.getNotification().getTitle());
        notificationcompat.setContentText(remoteMessage.getNotification().getBody());
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationcompat.setSound(defaultSoundUri);
        notificationcompat.setAutoCancel(true);
        notificationcompat.setSmallIcon(R.mipmap.ic_launcher);
        notificationcompat.setChannelId(remoteMessage.getNotification().getTitle());
        notificationcompat.setContentIntent(pendingIntent);

//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        NotificationManager notificationManager2 = getSystemService(NotificationManager.class);
//        notificationManager.notify(notificationId,notificationcompat.build());
//        startForeground(notificationId, notificationcompat.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getTitle(), importance);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setLightColor(Color.BLUE);
            mChannel.setDescription(remoteMessage.getNotification().getBody());
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManager notificationManager2 = getSystemService(NotificationManager.class);
        notificationManager.notify(notificationId,notificationcompat.build());
        startForeground(notificationId, notificationcompat.build());
    }
}
