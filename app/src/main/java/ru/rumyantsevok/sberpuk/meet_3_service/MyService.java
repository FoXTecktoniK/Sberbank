package ru.rumyantsevok.sberpuk.meet_3_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;

import static ru.rumyantsevok.sberpuk.meet_3_service.SecondActivity.DATA_KEY;

public class MyService extends Service {

    private static final String[] names = new String[]{
            "Nohemi Zwart",
            "Elda Castiglione",
            "Ty Brummond",
            "Tobie Bischoff",
            "Clarine Duffield",
            "Janise Boren",
            "Ahmad Lyda",
            "Yasuko Hilario",
            "Nila Pavao",
            "Sherly Fuson",
            "Rachele Brammer",
            "Solange Suitt",
            "Sharron Ransdell",
            "Shannan Stanger",
            "Huey Rounds",
            "Raeann Buttler",
            "Bernice Martino",
            "Dessie Nading",
            "Buddy Lock",
            "Evan Guerrant",
            "Christy Anguiano",
            "Michael Tekulve",
            "Porter Lytle",
            "Shelly Vreeland",
            "Elza Chatfield",
            "Lynell Hawthorn",
            "Marquetta Heitmann",
            "Stephnie Holleman",
            "Brandee Matley",
            "Jacquetta Millen"
    };

    private LocalBinder binder = new LocalBinder();

    private WeakReference<Messenger> messengerWeakReference;

    private final Thread executingThread = new Thread(() -> {
        final Random random = new Random(System.currentTimeMillis());
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
            if (messengerWeakReference.get() != null) {
                final int position = random.nextInt(names.length);
                final Message message = Message.obtain();
                final Bundle data = new Bundle();
                data.putString(DATA_KEY, names[position]);
                message.what = SecondActivity.NEW_DATA;
                message.setData(data);
                try {
                    messengerWeakReference.get().send(message);
                } catch (RemoteException ignored) {
                }
            }
        }

    });

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSendingMessages();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executingThread.interrupt();
    }

    private void startSendingMessages() {
        executingThread.start();
    }

    class LocalBinder extends Binder {
        void setMessenger(@NonNull final Messenger messenger) {
            messengerWeakReference = new WeakReference<>(messenger);
        }
    }
}
