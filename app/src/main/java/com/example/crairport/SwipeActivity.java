package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//implements View.OnTouchListener,
//        GestureDetector.OnGestureListener, View.OnDragListener

public class SwipeActivity extends AppCompatActivity implements GamePopFragment.GamePopFragmentListener {

//    float dX;
//    float dY;
//    int lastAction;
    int totalPoints, currentPoints;
    int index_1, index_2;
    ArrayList<String> phraseOneArray = new ArrayList<>();
    ArrayList<String> phraseTwoArray = new ArrayList<>();


//    private ImageView rightImage, leftImage;
//    private GestureDetector gDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

//        rightImage = (ImageView) findViewById(R.id.rightImage);
//        leftImage = (ImageView) findViewById(R.id.leftImage);

//        rightImage.setOnTouchListener(this);
//        leftImage.setOnTouchListener(this);
//        gDetector = new GestureDetector(this, this);

        // Read file
        Intent current = getIntent();
        // Change this for every level
        try {
            InputStream in = getAssets().open("SwipeData/Level1/Phrase1.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String phrase = "";
            while ((phrase = bf.readLine()) != null) {
                phraseOneArray.add(phrase);
            }

            InputStream in2 = getAssets().open("SwipeData/Level1/Phrase2.txt");
            BufferedReader bf2 = new BufferedReader(new InputStreamReader(in2));
            String phrase2 = "";
            while ((phrase2 = bf2.readLine()) != null) {
                phraseTwoArray.add(phrase2);
            }

            index_1 = 0;
            TextView leftCard = (TextView) findViewById(R.id.leftCard);
            leftCard.setText(phraseOneArray.get(index_1));

            index_2 = _generateNextIndexForPhrase2(index_1, phraseTwoArray.size());

            TextView rightCard = (TextView) findViewById(R.id.rightCard);
            rightCard.setText(phraseTwoArray.get(index_2));



//            // Randomly pick two indexes
//            Random randomizer = new Random();
//            int firstIdx = randomizer.nextInt(allPossible.size());
//            int secondIdx = randomizer.nextInt(allPossible.size());
//
//            TextView leftCard = (TextView) findViewById(R.id.leftCard);
//            TextView rightCard = (TextView) findViewById(R.id.rightCard);
//
//            leftCard.setText(allPossible.get(firstIdx));
//            rightCard.setText(allPossible.get(secondIdx));
        } catch (IOException e) {
            e.printStackTrace();
        }

        totalPoints = phraseOneArray.size();
        TextView scoreText = (TextView) findViewById(R.id.scoreText);

        scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

    }
//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//        gDetector.onTouchEvent(event);
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                dX = view.getX() - event.getRawX();
//                dY = view.getY() - event.getRawY();
//                lastAction = MotionEvent.ACTION_DOWN;
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                view.setY(event.getRawY() + dY);
//                view.setX(event.getRawX() + dX);
//                lastAction = MotionEvent.ACTION_MOVE;
//                break;
//
//            case MotionEvent.ACTION_UP:
//                break;
//
//            default:
//                return false;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onDown(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent motionEvent) {
//        return false;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        return false;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent motionEvent) {
//
//    }
//
//    @Override
//    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//        return false;
//    }
//
//    @Override
//    public boolean onDrag(View view, DragEvent dragEvent) {
//        return true;
//    }
//

    public void onClickMatched(View view) {

        if (index_1 > phraseOneArray.size() - 1) {
            String score = String.format("%s / %s", currentPoints, totalPoints);
            showNoticeDialog(score);
            return;
        }

        // Set scores
        currentPoints += index_1 == index_2 ? 1 : 0;
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

        //TODO: Add Toast here
        Toast.makeText(SwipeActivity.this, index_1 != index_2 ? "Incorrect" : "Correct",
                Toast.LENGTH_SHORT).show();

        //TODO: Go to next index
        if (index_1 == phraseOneArray.size() - 1) {
            // Add Toast to say that game is completed
            String score = String.format("%s / %s", currentPoints, totalPoints);
            showNoticeDialog(score);
            index_1 += 1;
            return;
        }

        index_1 += 1;
        TextView leftCard = (TextView) findViewById(R.id.leftCard);
        leftCard.setText(phraseOneArray.get(index_1));
        index_2 = _generateNextIndexForPhrase2(index_1, phraseOneArray.size());
        TextView rightCard = (TextView) findViewById(R.id.rightCard);
        rightCard.setText(phraseTwoArray.get(index_2));

        return;
    }

    public void onClickNotMatch(View view) {

        if (index_1 > phraseOneArray.size() - 1) {
            String score = String.format("%s / %s", currentPoints, totalPoints);
            showNoticeDialog(score);
            return;
        }
        // Set scores
        currentPoints += index_1 != index_2 ? 1 : 0;
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

        Toast.makeText(SwipeActivity.this, index_1 == index_2 ? "Incorrect" : "Correct",
                Toast.LENGTH_SHORT).show();

        //TODO: Go to next index

        if (index_1 == phraseOneArray.size() - 1) {
            // Add Toast to say that game is completed

            String score = String.format("%s / %s", currentPoints, totalPoints);
            showNoticeDialog(score);
            index_1 += 1;
            return;
        }

        index_1 += 1;
        TextView leftCard = (TextView) findViewById(R.id.leftCard);
        leftCard.setText(phraseOneArray.get(index_1));
        index_2 = _generateNextIndexForPhrase2(index_1, phraseOneArray.size());
        TextView rightCard = (TextView) findViewById(R.id.rightCard);
        rightCard.setText(phraseTwoArray.get(index_2));

        return;
    }

    public int _generateNextIndexForPhrase2(int index_phrase_1, int totalLength) {
        double probability = Math.random();

        return probability < 0.5 ? index_phrase_1 : (2*index_phrase_1 % totalLength);
    }

    public void showNoticeDialog(String s) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new GamePopFragment(s) ;
        dialog.show(getFragmentManager(), "Complete Activity Listener");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

        // Routing to the next game


        return;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        return;
    }

}
