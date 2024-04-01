package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView verEmail = findViewById(R.id.invalidemail);
        TextView verPassword = findViewById(R.id.invalidpassword);
        EditText email = findViewById(R.id.EmailAddress);
        EditText password = findViewById(R.id.TextPassword);
        Button loginBtn = findViewById(R.id.login);
        TextView sign = findViewById(R.id.signup);
        SharedPreferences sharedSignIn = getSharedPreferences("shared_SignIn", MODE_PRIVATE);
        SharedPreferences sharedLogin = getSharedPreferences("shared_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedLogin.edit();

        String validEmail = sharedSignIn.getString("email","");


        String validPassword = sharedSignIn.getString("password","");


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verify email
                if (!email.getText().toString().equals(validEmail) && !validEmail.equals("")) {
                    verEmail.setText("email address does not exist");
                }
                //verify password
                else if (!password.getText().toString().equals(validPassword) && !validPassword.equals("")) {
                    verPassword.setText("Incorect password. Please try again");
                    verEmail.setText("");

                } else {
                    //save the email and password in shared preferences
                    editor.putString("email", email.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.apply();
                    // Create an Intent to start the SecondActivity
                    Intent intent = new Intent(login.this, MapsActivity.class);
                    startActivity(intent);
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });








    }
}