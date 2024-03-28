package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        TextView login = findViewById(R.id.login);
        Button signBtn = findViewById(R.id.sign);
        SharedPreferences sharedSignIn = getSharedPreferences("shared_SignIn", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedSignIn.edit();
        SharedPreferences sharedLogin = getSharedPreferences("shared_login", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedLogin.edit();

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the email and password in shared preferences
                editor.putString("email", email.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.apply();

                editor2.putString("email", email.getText().toString());
                editor2.putString("password", password.getText().toString());
                editor2.apply();
                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(signup.this, home.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });








    }
}