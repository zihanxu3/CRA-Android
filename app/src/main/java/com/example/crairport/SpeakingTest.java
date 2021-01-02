package com.example.crairport;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SpeakingTest extends AppCompatActivity {


    private int index;
    private ArrayList<String> allText;
    private MediaPlayer mediaPlayer;
    private ImageView recordButton;
    private MediaRecorder recorder;
    private File outputFile;
    private boolean isRecording = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking_test);
        recordButton = (ImageView) findViewById(R.id.recordButton);

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

        //Record Sound
        outputFile = new File( "voiceRecording.mp3");
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
            mediaPlayer.setVolume(1, 1);
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

    public void onRecording(View view) {

        //TODO: This works but this needs to be tested on an actual device
        // You might have to fix this for future android versions as well
        if (!isRecording) {
            try {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.OutputFormat.AAC_ADTS);
                recorder.setOutputFile(outputFile);


                recorder.prepare();
                recorder.start();
                Toast.makeText(getApplicationContext(), "Recording started",
                        Toast.LENGTH_LONG).show();
                isRecording = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(getApplicationContext(), "Audio Recorder successfully",
                    Toast.LENGTH_LONG).show();
        }
    }
}