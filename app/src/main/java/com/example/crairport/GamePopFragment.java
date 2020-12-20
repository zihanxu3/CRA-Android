package com.example.crairport;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


public class GamePopFragment extends DialogFragment {
    public GamePopFragment() { }

    String score = "";

    @SuppressLint("ValidFragment")
    public GamePopFragment(String s) {
        score = s;
    }
    public interface GamePopFragmentListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    GamePopFragmentListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (GamePopFragmentListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(e.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Congratulations!")
                .setMessage("Your score is " + score)
                .setPositiveButton("Next Activity", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        listener.onDialogPositiveClick(GamePopFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        listener.onDialogNegativeClick(GamePopFragment.this);
                    }
                });
        return builder.create();
    }
}





// TRASH CODE:

//.setPositiveButton("Next Activity", new DialogInterface.OnClickListener() {
//public void onClick(DialogInterface dialog, int id) {
//        // FIRE ZE MISSILES!
//        }
//        })
//        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//public void onClick(DialogInterface dialog, int id) {
//        // User cancelled the dialog
//        }
//        });