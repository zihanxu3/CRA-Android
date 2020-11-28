package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SwipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

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

            // Randomly pick two indexes
            Random randomizer = new Random();
            int firstIdx = randomizer.nextInt(allPossible.size());
            int secondIdx = randomizer.nextInt(allPossible.size());

            TextView leftCard = (TextView) findViewById(R.id.leftCard);
            TextView rightCard = (TextView) findViewById(R.id.rightCard);

            leftCard.setText(allPossible.get(firstIdx));
            rightCard.setText(allPossible.get(secondIdx));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}