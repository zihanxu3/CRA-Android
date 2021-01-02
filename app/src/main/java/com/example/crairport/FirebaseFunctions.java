package com.example.crairport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class FirebaseFunctions {

    private FirebaseUser user;

    //From firebase labels
    final protected String userCollection = "users";
    final protected String firstNameField = "first name";
    final protected String experienceField = "experience";
    final protected String levelField = "level";

    public FirebaseFunctions() {
        user = null;
    }

    public FirebaseFunctions(FirebaseUser user) {
        this.user = user;
    }

    /**
     * Gneerates a report to send to Firebase
     * @param game name of the game
     * @param level current level played on
     * @param correct percent correct
     */
    public void createReport(String game, int level, double correct) {
        String email = user.getEmail();

        HashMap<String, Object> data = new HashMap<>();
        data.put("Game", game);
        data.put("Level", level);
        data.put("Percent Correct", correct);

        FirebaseFirestore.getInstance().collection("users").document(email)
                .collection("reports").document().set(data);
    }
}
