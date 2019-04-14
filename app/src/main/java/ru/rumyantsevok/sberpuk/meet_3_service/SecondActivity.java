package ru.rumyantsevok.sberpuk.meet_3_service;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import ru.rumyantsevok.sberpuk.R;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class SecondActivity extends AppCompatActivity {

    public final static int NEW_DATA = 322;
    public static final String DATA_KEY = "DATA";

    private static class MyHandler extends Handler {

        private WeakReference<SecondActivity> activity;

        MyHandler(@NonNull final SecondActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final SecondActivity act = activity.get();
            if (act != null && msg.what == NEW_DATA) {
                act.dataTextView.setText(msg.getData().getString(DATA_KEY, "EMPTY"));
            }
        }
    }

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof MyService.LocalBinder) {
                binder = (MyService.LocalBinder) service;
                binder.setMessenger(messenger);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger messenger = new Messenger(new MyHandler(this));
    private TextView dataTextView;
    private MyService.LocalBinder binder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    private void init() {
        findViewById(R.id.kill_service).setOnClickListener((v -> {
            final Intent intent = new Intent(this, MyService.class);
            unbindService();
            stopService(intent);
        }));
        dataTextView = findViewById(R.id.data_tv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    private void bindService() {
        final Intent intent = new Intent(this, MyService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService();
    }

    private void unbindService() {
        if (binder != null) {
            binder.interrupt();
        }
        unbindService(connection);
    }
}
