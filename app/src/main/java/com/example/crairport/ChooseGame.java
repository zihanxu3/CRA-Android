package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseGame extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
    }

    public void onMatchingGame(View view) {
        Intent nextScreen = new Intent(this, SwipeActivity.class);

        Intent currentScreen = getIntent();
        int level = currentScreen.getIntExtra("level", 1);
        String mode = "image";

        // TODO: `mode` is adaptive to the game process, it takes values `phrase` or `image`
        nextScreen.putExtra("level", level);
        nextScreen.putExtra("Mode", mode);
        startActivity(nextScreen);
    }

    public void onSpeakingGame(View view) {
        Intent nextScreen = new Intent(this, SpeakingTest.class);
        int level = this.getIntent().getIntExtra("level", 1);
        nextScreen.putExtra("level", level);
        startActivity(nextScreen);
    }

    public void onBowlingGame(View view) {
        Intent currentScreen = getIntent();
        Intent nextScreen = new Intent(this, BowlingGame.class);
        int level = currentScreen.getIntExtra("level", 1);
        nextScreen.putExtra("level", level);
        startActivity(nextScreen);
    }
}