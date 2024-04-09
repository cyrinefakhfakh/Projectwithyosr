package com.example.project;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by Dytstudio.
 */

public class BaseActivity extends AppCompatActivity {
    public void setTitle(int toolbarId,int titleId, String title, int btnDrawable, int colorBg, int titleColor){
        Toolbar toolbar = findViewById(toolbarId);
        toolbar.setBackgroundResource(colorBg);
        setSupportActionBar(toolbar);
        TextView pageTitle = toolbar.findViewById(titleId);
        pageTitle.setText(title);
        pageTitle.setTextColor(getResources().getColor(titleColor));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(btnDrawable);
    }
    public void setTitleHome(int toolbarId,int image, int btnDrawable, int imageTitle){
        Toolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageView pageTitle = toolbar.findViewById(image);
        pageTitle.setImageResource(imageTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(btnDrawable);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
