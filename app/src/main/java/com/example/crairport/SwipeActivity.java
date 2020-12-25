package com.example.crairport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity implements GamePopFragment.GamePopFragmentListener {

    int totalPoints, currentPoints;
    int index_1, index_2;

    String mode = "";
    ArrayList<String> phraseOneArray = new ArrayList<>();
    ArrayList<String> phraseTwoArray = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        // Read file
        Intent current = getIntent();

        String level = current.getStringExtra("Level");
        this.mode = current.getStringExtra("Mode");

        if (mode.equals("phrase")) {
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
                TextView leftCard = (TextView) findViewById(R.id.leftCard);
                leftCard.setText(phraseOneArray.get(index_1));

                index_2 = _generateNextIndexForPhrase2(index_1, phraseTwoArray.size());

                TextView rightCard = (TextView) findViewById(R.id.rightCard);
                rightCard.setText(phraseTwoArray.get(index_2));

                System.out.println(phraseOneArray);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String fileName1 = String.format("SwipeData/Level%s/Image.txt", level);
            String fileName2 = String.format("SwipeData/Level%s/Phrase.txt", level);

            System.out.println("reached line 41");
            System.out.println(fileName1);
            // Change this for every level
            try {
                InputStream in = getAssets().open(fileName1);
                BufferedReader bf = new BufferedReader(new InputStreamReader(in));
                String phrase = "";
                while ((phrase = bf.readLine()) != null) {
                    phraseOneArray.add("a" + phrase);
                }

                InputStream in2 = getAssets().open(fileName2);
                BufferedReader bf2 = new BufferedReader(new InputStreamReader(in2));
                String phrase2 = "";
                while ((phrase2 = bf2.readLine()) != null) {
                    phraseTwoArray.add(phrase2);
                }

                index_1 = 0;
                ImageView leftCard = (ImageView) findViewById(R.id.leftImage);
                String url = String.format("@drawable/%s", phraseOneArray.get(index_1));

                int imageResource = getResources().getIdentifier(url, null, getPackageName());
                Drawable res = getApplicationContext().getDrawable(imageResource);
                leftCard.setImageDrawable(res);

                index_2 = _generateNextIndexForPhrase2(index_1, phraseTwoArray.size());

                TextView rightCard = (TextView) findViewById(R.id.rightCard);
                rightCard.setText(phraseTwoArray.get(index_2));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        totalPoints = phraseOneArray.size();
        TextView scoreText = (TextView) findViewById(R.id.scoreText);

        scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

    }


    public void onClickMatched(View view) {

        if (index_1 > phraseOneArray.size() - 1) {
            String score = String.format("%s / %s", currentPoints, totalPoints);
            showNoticeDialog(score);
            return;
        }

        if (mode.equals("phrase")) {
            // Set scores
            currentPoints += index_1 == index_2 || phraseOneArray.get(index_1) == phraseOneArray.get(index_2) ? 1 : 0;
            TextView scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

            //TODO: Add Toast here
            Toast.makeText(SwipeActivity.this, index_1 == index_2 || phraseOneArray.get(index_1) == phraseOneArray.get(index_2) ? "Correct" : "Incorrect",
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
        } else {
            // Set scores
            currentPoints += index_1 == index_2 ? 1 : 0;
            TextView scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

            //TODO: Add Toast here
            Toast.makeText(SwipeActivity.this, index_1 == index_2 ? "Correct" : "Incorrect",
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
            ImageView leftCard = (ImageView) findViewById(R.id.leftImage);
            String url = String.format("@drawable/%s", phraseOneArray.get(index_1));
            System.out.println(url);
            int imageResource = getResources().getIdentifier(url, null, getPackageName());
            @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getApplicationContext().getDrawable(imageResource);
            leftCard.setImageDrawable(res);

            index_2 = _generateNextIndexForPhrase2(index_1, phraseOneArray.size());
            TextView rightCard = (TextView) findViewById(R.id.rightCard);
            rightCard.setText(phraseTwoArray.get(index_2));
        }
        return;
    }

    public void onClickNotMatch(View view) {

        if (index_1 > phraseOneArray.size() - 1) {
            String score = String.format("%s / %s", currentPoints, totalPoints);
            showNoticeDialog(score);
            return;
        }

        if (mode.equals("phrase")) {
            // Set scores
            currentPoints += index_1 == index_2 || phraseOneArray.get(index_1) == phraseOneArray.get(index_2) ? 0 : 1;
            TextView scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setText(String.format("Score : %s / %s", currentPoints, totalPoints));

            Toast.makeText(SwipeActivity.this, index_1 == index_2 || phraseOneArray.get(index_1) == phraseOneArray.get(index_2) ? "Incorrect" : "Correct",
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
        } else {
            currentPoints += index_1 == index_2 ? 0 : 1;
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
            ImageView leftCard = (ImageView) findViewById(R.id.leftImage);
            String url = String.format("@drawable/%s", phraseOneArray.get(index_1));
            System.out.println(url);
            int imageResource = getResources().getIdentifier(url, null, getPackageName());
            @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getApplicationContext().getDrawable(imageResource);
            leftCard.setImageDrawable(res);

            index_2 = _generateNextIndexForPhrase2(index_1, phraseOneArray.size());
            TextView rightCard = (TextView) findViewById(R.id.rightCard);
            rightCard.setText(phraseTwoArray.get(index_2));
        }
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

        // TODO: Update this for every level
        int level = 1;
        double correct = currentPoints * 1.0 / totalPoints;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFunctions functions = new FirebaseFunctions(user);
        functions.createReport("Match 'n Swipe", level, correct);
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
