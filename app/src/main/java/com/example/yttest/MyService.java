package com.example.yttest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {

    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // write code for creating foreground service

        createNotificationChannel();

        Intent intent1 = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification notification = new NotificationCompat.Builder(this, "ChannelId1")
                .setContentTitle("My App Tutorial")
                .setContentText("Our app is running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build();


        startForeground(1, notification);

//        playSound();

//        System.out.println("this ran");
        /*Toast.makeText(getApplicationContext(),"Hello Java point", Toast.LENGTH_LONG).show();

        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(getApplicationContext(), 1, intent2, 0);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("this ran");
                File file = new File("");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                Notification pushNoti = new Notification.Builder(getApplicationContext())
                        .setContentTitle("periodic title")
                        .setContentText("periodic text")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .addAction(R.drawable.ic_launcher_background,"chat",pendingIntent2)
                        .setContentIntent(pendingIntent2).build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(2,pushNoti);

            }
        };

        timer.scheduleAtFixedRate(task,0,10000);*/

//        AlarmManager mgr=(AlarmManager)getSystemService(this.ALARM_SERVICE);
//        Intent i=new Intent(this, MainActivity.class);
//        PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, 0);
//
//        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, this.elapsedRealtime(), PERIOD, pi);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
//                System.out.println("this ran");
                playSound();
            }
        };

        timer.scheduleAtFixedRate(task,0,60000);

        return START_STICKY;
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "ChannelId1", "Foreground notification", NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        stopForeground(true);
        stopSelf();

        super.onDestroy();
    }

    public void playSound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(MyService.this,R.raw.glass_sound);
        mediaPlayer.start();
    }
}
