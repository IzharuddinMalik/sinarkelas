package com.sinarkelas.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.sinarkelas.R;
import com.sinarkelas.ui.kelas.DetailKelasActivity;
import com.sinarkelas.ui.notifikasi.NotifikasiActivity;

import java.util.Random;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("tipenotif"));
    }

    private void showNotification(String message, String tipenotif) {

        if (tipenotif.equals("NOTIFADMIN")){
            final int not_nu = generateRandom();

            // sound
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.pesanopen);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();

            Intent i = new Intent(this, NotifikasiActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mChannel.setShowBadge(true);

                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Info Sinar Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntent(i);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                mBuilder.setContentIntent(resultPendingIntent);
                notificationManager.notify(not_nu, mBuilder.build());
            }else {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Info Sinar Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);
                mBuilder.setContentIntent(pendingIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(not_nu, mBuilder.build());
            }
        } else if (tipenotif.equals("NOTIFPOSTGURU")){
            final int not_nu = generateRandom();

            // sound
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.pesanopen);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();

            Intent i = new Intent(this, DetailKelasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mChannel.setShowBadge(true);

                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntent(i);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                mBuilder.setContentIntent(resultPendingIntent);
                notificationManager.notify(not_nu, mBuilder.build());
            }else {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);
                mBuilder.setContentIntent(pendingIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(not_nu, mBuilder.build());
            }
        } else if (tipenotif.equals("NOTIFPOSTMAHASISWA")){
            final int not_nu = generateRandom();

            // sound
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.pesanopen);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();

            Intent i = new Intent(this, DetailKelasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mChannel.setShowBadge(true);

                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntent(i);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                mBuilder.setContentIntent(resultPendingIntent);
                notificationManager.notify(not_nu, mBuilder.build());
            }else {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);
                mBuilder.setContentIntent(pendingIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(not_nu, mBuilder.build());
            }
        } else if (tipenotif.equals("NOTIFTUGAS")){
            final int not_nu = generateRandom();

            // sound
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.pesanopen);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();

            Intent i = new Intent(this, DetailKelasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mChannel.setShowBadge(true);

                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Tugas Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntent(i);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                mBuilder.setContentIntent(resultPendingIntent);
                notificationManager.notify(not_nu, mBuilder.build());
            }else {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Tugas Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);
                mBuilder.setContentIntent(pendingIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(not_nu, mBuilder.build());
            }
        } else if (tipenotif.equals("NOTIFPRESENSI")){
            final int not_nu = generateRandom();

            // sound
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mMediaPlayer = MediaPlayer.create(this, R.raw.pesanopen);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.start();

            Intent i = new Intent(this, DetailKelasActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    mChannel.setShowBadge(true);

                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Presensi Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntent(i);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
                mBuilder.setContentIntent(resultPendingIntent);
                notificationManager.notify(not_nu, mBuilder.build());
            }else {
                PendingIntent pendingIntent = PendingIntent.getActivity(this, not_nu, i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.logo512pixelbaru)
                        .setContentTitle("Notifikasi Presensi Kelas")
                        .setContentText("" + message)
//                                .addAction(R.mipmap.ic_launcher, "Action", contentIntent)
                        .setAutoCancel(true)
                        .setSound(soundUri);
                mBuilder.setContentIntent(pendingIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(not_nu, mBuilder.build());
            }
        }
    }

    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}
