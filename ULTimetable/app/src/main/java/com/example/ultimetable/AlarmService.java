package com.example.ultimetable;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlarmService extends Service {

    public static String ACTION_ALARM = "action_alarm";
    public int id = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private static final String TAG = "MyService";

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "time: " + new Date().toString());
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
                builder.setContentTitle("Note Notification!")
                        .setSmallIcon(R.drawable.ic_menu_send)
                        .setContentText("")
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setFullScreenIntent(pi, true)
                        .setAutoCancel(true)
                        .setContentIntent(pi)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);
                //   API 26，Android 8.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(notificationChannel);
                    builder.setChannelId("AppTestNotificationId");
                }
                notificationManager.notify(id++, builder.build());
            }
        }).start();
        flags =  START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

}

