package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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

public class SwipeActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener, View.OnDragListener {

    float dX;
    float dY;
    int lastAction;
    private ImageView rightImage, leftImage;
    private GestureDetector gDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        rightImage = (ImageView) findViewById(R.id.rightImage);
        leftImage = (ImageView) findViewById(R.id.leftImage);

        rightImage.setOnTouchListener(this);
        leftImage.setOnTouchListener(this);
        gDetector = new GestureDetector(this, this);

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

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        gDetector.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        return true;
    }
}