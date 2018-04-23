package com.pythonteam.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;


public class SplashActivity extends AppCompatActivity {

    private Process hilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        ProgressBar pg = findViewById(R.id.progressBar);
        hilo = new Process(pg,getApplicationContext());
        hilo.execute();
        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hilo.cancel(true);
        finish();
    }
}
