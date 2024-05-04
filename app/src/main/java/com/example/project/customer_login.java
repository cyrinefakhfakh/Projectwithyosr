package com.example.project;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customer_login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        EditText Email = findViewById(R.id.EmailAddress);
        EditText Password = findViewById(R.id.TextPassword);
        Button loginBtn = findViewById(R.id.login);
        TextView sign = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(customer_login.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(customer_login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Get user's unique ID
                                        String userId = user.getUid();
                                        // Add the user to the database
                                        usersRef.child(userId).child("email").setValue(user.getEmail());
                                        // Start the customer_map activity
                                        Intent intent = new Intent(customer_login.this, customer_map.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(customer_login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(customer_login.this, customer_signup.class);
                startActivity(intent);
            }
        });
    }
}
