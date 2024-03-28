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
        SharedPreferences sharedLogin = getSharedPreferences("shared_login", MODE_PRIVATE);
        if (sharedLogin.contains("email")){
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
        }

    Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Create an Intent to start the SecondActivity
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
        }
    });


        Button sign = findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });



}



}