package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MatchingGame extends AppCompatActivity {

    private ViewGroup mainLayout;
    private ArrayList<ImageView> stars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_game);

        mainLayout = (ConstraintLayout) findViewById(R.id.layout);

        ImageView star = (ImageView) findViewById(R.id.GoldStar1);
        stars.add(star);
        star = (ImageView) findViewById(R.id.GoldStar2);
        stars.add(star);
        star = (ImageView) findViewById(R.id.GoldStar3);
        stars.add(star);


    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        };
    }
}