package com.example.crairport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountScreen extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_screen);
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Creates the user and stores it in firebase
     * @param view The Viewable object
     */
    public void onButtonCreate(View view) {

        String email = ((TextInputLayout)findViewById(R.id.emailField))
                .getEditText().getText().toString();
        String password = ((TextInputLayout)findViewById(R.id.passwordField))
                .getEditText().getText().toString();
        String firstName = ((TextInputLayout) findViewById(R.id.firstNameField))
                .getEditText().getText().toString();
        String lastName = ((TextInputLayout) findViewById(R.id.lastNameField))
                .getEditText().getText().toString();


        if (email.isEmpty() || !email.contains("@")) {
            // Send Invalid Alert
            sendInvalidAlert("Invalid Email");
            return;
        }
        if (password.isEmpty()) {
            //Send Invalid Alert
            sendInvalidAlert("Empty Password");
            return;
        }

        if (firstName.isEmpty()) {
            //Send Invalid Alert
            sendInvalidAlert("Empty First Name");
            return;
        }

        if (lastName.isEmpty()) {
            //Send Invalid Alert
            sendInvalidAlert("Empty Last Name");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        user = auth.getCurrentUser();
                        Toast.makeText(CreateAccountScreen.this, "Worked",
                                Toast.LENGTH_SHORT).show();
                        // Send to next screen
                        Map<String, Object> data = new HashMap<>();
                        data.put("experience", 0);
                        data.put("first name", firstName);
                        data.put("last name", lastName);
                        data.put("level", 1);
                        FirebaseFirestore.getInstance().collection("users")
                                .document(email).set(data);
                        finish();


                    }
                    else {
                        // Work on this
                        Toast.makeText(CreateAccountScreen.this, "User already exists",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onHaveAccountClick(View view) {
        finish();
    }

    /**
     * Sends an Alert Dialog
     * @param message to be shown
     */
    private void sendInvalidAlert(String message) {
        this.runOnUiThread(() -> {
            AlertDialog alertDialog = new AlertDialog.Builder(CreateAccountScreen.this,
                    R.style.LoginDialog).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });
    }
}