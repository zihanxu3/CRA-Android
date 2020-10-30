package com.example.crairport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
}