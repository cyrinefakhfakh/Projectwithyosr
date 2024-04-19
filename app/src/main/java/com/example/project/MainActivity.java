package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {

                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);
                }
            }
        };thread.start();

    }
}