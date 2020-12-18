package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SpeakingTest extends AppCompatActivity {


    private int index;
    private ArrayList<String> allText;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking_test);

        // Read file
        Intent current = getIntent();
        // Change this for every level
        ArrayList<String> allPossible = new ArrayList<>();
        try {
            InputStream in = getAssets().open("LevelData/Level1Phrases.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String phrase = "";
            while ((phrase = bf.readLine()) != null) {
                allPossible.add(phrase);
            }

            this.allText = allPossible;
            // Randomly pick idx
            Random randomizer = new Random();
            index = randomizer.nextInt(allPossible.size());

            TextView textView = findViewById(R.id.promptText);
            textView.setText(allPossible.get(index));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickAudio(View view) {
        // Read file
        Intent current = getIntent();
        // Change this for every level
        ArrayList<String> allPossible = new ArrayList<>();
        try {
            InputStream in = getAssets().open("LevelData/Level1Audio.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String audio = "";
            while ((audio = bf.readLine()) != null) {
                allPossible.add(audio);
            }

            //Get AudioFile name
            String audioFile = allPossible.get(index);
            int resId = getResources().getIdentifier(audioFile, "raw", getPackageName());
            mediaPlayer = MediaPlayer.create(this, resId);
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextTest(View view) {
        TextView textView = findViewById(R.id.promptText);
        this.index += 1;
        textView.setText(this.allText.get((index) % (this.allText.size())));
    }
}