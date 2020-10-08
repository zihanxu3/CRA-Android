package com.example.crairport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        String email = ((EditText)findViewById(R.id.editTextTextEmailAddress)).getText().toString();
        String password = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();


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
                //Intent nextScreen = new Intent(LoginScreen.this, REPLACE SCREEN CLASS);
                //startActivity(nextScreen);
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