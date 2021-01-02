package com.example.crairport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Setup firebase stuff here
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        TextView nameField = ((TextView)findViewById(R.id.nameField));

        //Get Firebase info
        FirebaseFunctions functions = new FirebaseFunctions(user);
        HashMap<String, Object> userInfo = new HashMap<>();
        String email = user.getEmail();
        FirebaseFirestore.getInstance().collection(functions.userCollection).document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isComplete()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            userInfo.put(functions.firstNameField, documentSnapshot.get(functions.firstNameField));
                            userInfo.put(functions.experienceField, documentSnapshot.get(functions.experienceField));
                            userInfo.put(functions.levelField, documentSnapshot.get(functions.levelField));
                            nameField.setText("Welcome Back, " + (String)userInfo.get(functions.firstNameField));
                        }
                    }
                });
    }

    /**
     * Goes to user indicated level
     * @param view the element clicked on
     */
    public void clickedLevel(View view) {
        Intent nextScreen = new Intent(this, MapActivity.class);
        //TODO: We did not do level progression yet, but if needed check what level
        // the user is in currently here

        int level = 0;
        switch (view.getId()) {
            case R.id.LevelOneImage:
                level = 1;
                break;
            case R.id.LevelTwoImage:
                level = 2;
                break;
            case R.id.levelThreeImage:
                level = 3;
                break;
            case R.id.levelFourImage:
                level = 4;
                break;
            case R.id.levelFiveImage:
                level = 5;
                break;
            default:
                level = 1;
        }
        nextScreen.putExtra("level", level);
        startActivity(nextScreen);
    }
}