package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void onClickStartChoose(View view) {
        Intent nextScreen = new Intent(this, ChooseGame.class);
        Intent currentScreen = this.getIntent();

        int currentLevel = currentScreen.getIntExtra("level", 1);
        nextScreen.putExtra("level", currentLevel);
        startActivity(nextScreen);
    }
}