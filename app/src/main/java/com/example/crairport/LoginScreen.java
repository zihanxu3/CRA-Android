package com.example.crairport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth AuthUser;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        AuthUser = FirebaseAuth.getInstance();
        setLoginDialog();
    }

    /**
     * Signs in user
     * @param view the viewable object
     */
    public void onSignEmail(View view) {

        //Start login dialog here
        loginDialog.show();

        TextInputLayout emailField = (TextInputLayout)findViewById(R.id.editTextTextEmailAddress);
        TextInputLayout passwordField = (TextInputLayout)findViewById(R.id.passwordField);
        String email = emailField.getEditText().getText().toString();
        String password = passwordField.getEditText().getText().toString();


        if (email.isEmpty() || !email.contains("@")) {
            // Send Invalid Alert
            sendInvalidAlert("Invalid Email");
            loginDialog.dismiss();
            return;
        }
        if (password.isEmpty()) {
            //Send Invalid Alert
            sendInvalidAlert("Empty Password");
            loginDialog.dismiss();
            return;
        }

        AuthUser.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                user = AuthUser.getCurrentUser();
                Intent nextScreen = new Intent(this, HomeScreen.class);
                startActivity(nextScreen);
            }
            else {
                //Send Invalid Alert
                sendInvalidAlert("Email or Password is incorrect");
            }
        });

        //End login dialog here
        loginDialog.dismiss();

    }

    /**
     * Sends user to next screen to create new user
     * @param view The viewable object
     */
    public void onCreateClick(View view) {
        Intent nextScreen = new Intent(this, CreateAccountScreen.class);
        startActivity(nextScreen);
    }

    /**
     * Sets up login dialog
     */
    private void setLoginDialog() {
        loginDialog = new ProgressDialog(this, R.style.LoginDialog);
        loginDialog.setTitle("Loading");
        loginDialog.setMessage("Please Wait... Logging in");
        loginDialog.setCancelable(false);
    }

    /**
     * Sends an Alert Dialog
     * @param message to be shown
     */
    private void sendInvalidAlert(String message) {
        this.runOnUiThread(() -> {
            AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen.this,
                    R.style.LoginDialog).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });
    }
}