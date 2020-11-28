package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseGame extends AppCompatActivity {

    private Intent currentScreen = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
    }

    public void onMatchingGame(View view) {
        Intent nextScreen = new Intent(this, SwipeActivity.class);
        //Fix this later
        //int level = currentScreen.getIntExtra("Level", 1);
        //nextScreen.putExtra("Level", level);
        startActivity(nextScreen);
    }
}