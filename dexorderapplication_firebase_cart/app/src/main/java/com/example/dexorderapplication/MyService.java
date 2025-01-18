package com.example.dexorderapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.example.dexorderapplication.Activity.MainActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyService extends Service {
    private static final String CHANNEL_ID = "DEX";
    private DatabaseReference orderDataRef;
    Integer a, deliveryState = 0;
    ArrayList<String> list = new ArrayList<>();
    Boolean isArrival = false;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        orderDataRef = FirebaseDatabase.getInstance().getReference("order");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            return START_STICKY;
        }
        a = intent.getIntExtra("a",0);
        isArrival = intent.getBooleanExtra("arrival",false);
        for (int i = 0; i < 1000; i++) {
            Log.d("MyService","onStartCommand:"+i);
        }

        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        Notification.Builder notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, CHANNELID)
                    .setContentText("앱이 실행중입니다...")
                    .setContentTitle("DEX")
                    .setSmallIcon(R.mipmap.ic_launcher);
        }

        startForeground(1001, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {

                f();
                if(deliveryState == 1){
                    createNotification();
                    if(isArrival){
                        stopForeground(true);
                        stopSelf();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    void f(){
        while (deliveryState == 0){
            try {
                Thread.sleep(5000);
                Log.d("a","a:"+a);
                orderDataRef.child("order"+a).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Log.d("MainActivity", "UserAccount : " + snapshot1.getValue());
                            list.add(snapshot1.getValue().toString());
                        }
                        if(list.size()>1){
                            deliveryState = Integer.valueOf(list.get(1));
                        }
                        Log.d("delivery","deliveryState:"+deliveryState);
                        list.clear();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel =
                    new NotificationChannel(CHANNEL_ID, "알림 설정 모드 타이틀", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(serviceChannel);
        }
        Intent notificationIntent = new Intent(this, NoticeArrival.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Dex")
                .setContentText("주문하신 물품이 도착했습니다.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}