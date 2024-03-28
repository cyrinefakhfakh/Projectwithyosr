package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button logout = findViewById(R.id.logout);
        SharedPreferences sharedLogin = getSharedPreferences("shared_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedLogin.edit();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(home.this, login.class);
                startActivity(intent);
            }
        });

    }
}