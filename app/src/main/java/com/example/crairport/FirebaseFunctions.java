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

    public HashMap<String, Object> getStarterInfo () {
        HashMap<String, Object> userInfo = new HashMap<>();
        String email = user.getEmail();
        FirebaseFirestore.getInstance().collection(userCollection).document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isComplete()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            userInfo.put(firstNameField, documentSnapshot.get(firstNameField));
                            userInfo.put(experienceField, documentSnapshot.get(experienceField));
                            userInfo.put(levelField, documentSnapshot.get(levelField));
                        }
                    }
                });
        return userInfo;
    }
}
