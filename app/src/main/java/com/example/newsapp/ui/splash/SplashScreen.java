package com.example.newsapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.newsapp.R;
import com.example.newsapp.ui.NewsActivity;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    private Context mContext = SplashScreen.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(2000);
                    Intent intent = new Intent(mContext, NewsActivity.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
