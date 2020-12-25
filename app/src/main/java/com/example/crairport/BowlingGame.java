package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BowlingGame extends AppCompatActivity implements GamePopFragment.GamePopFragmentListener {

    String msg;
    public View dragView;
    public View choice1, choice2;
    public int index_1, index_2;
    int totalPoints, currentPoints;
    private android.widget.RelativeLayout.LayoutParams layoutParams;

    ArrayList<String> phraseOneArray = new ArrayList<>();
    ArrayList<String> phraseTwoArray = new ArrayList<>();
    int currentCorrectId;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling_game);

        Intent current = getIntent();

        String level = current.getStringExtra("Level");
        // View to drag
        dragView = (TextView) findViewById(R.id.dragView);

        // View to drag onto
        choice1 = (TextView) findViewById(R.id.word_one);
        choice2 = (TextView) findViewById(R.id.word_two);

        //set touch listeners
        dragView.setOnTouchListener(new ChoiceTouchListener());


        //set drag listeners
        choice1.setOnDragListener(new ChoiceDragListener());
        choice2.setOnDragListener(new ChoiceDragListener());




        String fileName1 = String.format("SwipeData/Level%s/Phrase1.txt", level);
        String fileName2 = String.format("SwipeData/Level%s/Phrase2.txt", level);

        System.out.println("reached line 41");
        System.out.println(fileName1);
        // Change this for every level
        try {
            InputStream in = getAssets().open(fileName1);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String phrase = "";
            while ((phrase = bf.readLine()) != null) {
                phraseOneArray.add(phrase);
            }

            InputStream in2 = getAssets().open(fileName2);
            BufferedReader bf2 = new BufferedReader(new InputStreamReader(in2));
            String phrase2 = "";
            while ((phrase2 = bf2.readLine()) != null) {
                phraseTwoArray.add(phrase2);
            }

            index_1 = 0;
            ((TextView) dragView).setText(phraseOneArray.get(index_1));
            ((TextView) choice1).setText(phraseTwoArray.get(index_1));
            currentCorrectId = R.id.word_one;

            index_2 = _generateNextIndexForPhrase2(index_1, phraseTwoArray.size());

            System.out.println("-------------------------------");
            System.out.println(index_1);
            System.out.println(index_2);

            ((TextView) choice2).setText(phraseTwoArray.get(index_2));

        } catch (IOException e) {
            e.printStackTrace();
        }

        totalPoints = phraseOneArray.size();
        TextView scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));
    }

    private final class ChoiceTouchListener implements OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                /*
                 * Drag details: we only need default behavior
                 * - clip data could be set to pass data as part of drag
                 * - shadow can be tailored
                 */
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;

                    if (dropTarget.getId() == currentCorrectId) {

                        System.out.println(index_1);
                        if (index_1 >= totalPoints) {
                            String score = String.format("%s / %s", currentPoints, totalPoints);
                            showNoticeDialog(score);
                            break;
                        }

                        Toast toast = Toast.makeText(BowlingGame.this, "Correct!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        currentPoints += 1;
                        TextView scoreText = (TextView) findViewById(R.id.scoreText);
                        scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

                        if (index_1 + 1 == totalPoints) {
                            index_1 += 1;
                            String score = String.format("%s / %s", currentPoints, totalPoints);
                            showNoticeDialog(score);
                            break;
                        }

                        dragView = (TextView) findViewById(R.id.dragView);
                        // View to drag onto
                        choice1 = (TextView) findViewById(R.id.word_one);
                        choice2 = (TextView) findViewById(R.id.word_two);

                        index_1 += 1;

                        ((TextView) dragView).setText(phraseOneArray.get(index_1));
                        index_2 = _generateNextIndexForPhrase2(index_1, phraseTwoArray.size());

                        if (index_1 % 5 == 1 || index_1 % 5 == 3) {
                            ((TextView) choice1).setText(phraseTwoArray.get(index_1));
                            ((TextView) choice2).setText(phraseTwoArray.get(index_2));
                            currentCorrectId = R.id.word_one;
                        } else {
                            ((TextView) choice2).setText(phraseTwoArray.get(index_1));
                            ((TextView) choice1).setText(phraseTwoArray.get(index_2));
                            currentCorrectId = R.id.word_two;
                        }

                    } else {
                        System.out.println(index_1);
                        if (index_1 >= totalPoints) {
                            String score = String.format("%s / %s", currentPoints, totalPoints);
                            showNoticeDialog(score);
                            break;
                        }
                        Toast toast = Toast.makeText(BowlingGame.this, "Incorrect!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        if (index_1 + 1 == totalPoints) {
                            index_1 += 1;
                            String score = String.format("%s / %s", currentPoints, totalPoints);
                            showNoticeDialog(score);
                            break;
                        }

                        dragView = (TextView) findViewById(R.id.dragView);
                        // View to drag onto
                        choice1 = (TextView) findViewById(R.id.word_one);
                        choice2 = (TextView) findViewById(R.id.word_two);

                        index_1 += 1;

                        ((TextView) dragView).setText(phraseOneArray.get(index_1));
                        index_2 = _generateNextIndexForPhrase2(index_1, phraseTwoArray.size());

                        if (index_1 % 5 == 1 || index_1 % 5 == 3) {
                            ((TextView) choice1).setText(phraseTwoArray.get(index_1));
                            ((TextView) choice2).setText(phraseTwoArray.get(index_2));
                            currentCorrectId = R.id.word_one;
                        } else {
                            ((TextView) choice2).setText(phraseTwoArray.get(index_1));
                            ((TextView) choice1).setText(phraseTwoArray.get(index_2));
                            currentCorrectId = R.id.word_two;
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }


    public int _generateNextIndexForPhrase2(int index_phrase_1, int totalLength) {

        return ((2 * index_phrase_1 + 3 )% totalLength);
    }

    public void showNoticeDialog(String s) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new GamePopFragment(s) ;
        dialog.show(getFragmentManager(), "Complete Activity Listener");

        // TODO: Update this for every level
        int level = 1;
        double correct = currentPoints * 1.0 / totalPoints;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFunctions functions = new FirebaseFunctions(user);
        functions.createReport("Bowling Game", level, correct);
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

        // TODO: Routing to the next game

        return;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        return;
    }
}