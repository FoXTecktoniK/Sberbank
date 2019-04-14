package ru.rumyantsevok.sberpuk.meet_2_activities;

import androidx.appcompat.app.AppCompatActivity;
import ru.rumyantsevok.sberpuk.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((TextView) findViewById(R.id.textView)).setText(TAG);
        final Button button = findViewById(R.id.button);
        button.setText("Next");
        button.setOnClickListener((v) -> {
            final Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);
        });
    }
}
