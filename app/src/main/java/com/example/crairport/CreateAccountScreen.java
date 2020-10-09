package com.example.crairport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        String email = ((EditText)findViewById(R.id.editTextTextEmailAddress)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();

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

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        user = auth.getCurrentUser();
                        Toast.makeText(CreateAccountScreen.this, "Worked",
                                Toast.LENGTH_SHORT).show();
                        // Send to next screen

                    }
                    else {
                        // Work on this
                        Toast.makeText(CreateAccountScreen.this, "User already exists",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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