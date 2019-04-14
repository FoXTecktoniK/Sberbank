package ru.rumyantsevok.sberpuk.meet_3_service;

import androidx.appcompat.app.AppCompatActivity;
import ru.rumyantsevok.sberpuk.R;

import android.content.Intent;
import android.os.Bundle;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        init();
    }

    private void init() {
        findViewById(R.id.start_service_btn).setOnClickListener((v) -> {
            final Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });

        findViewById(R.id.start_sec_activity).setOnClickListener((v -> {
            final Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}
