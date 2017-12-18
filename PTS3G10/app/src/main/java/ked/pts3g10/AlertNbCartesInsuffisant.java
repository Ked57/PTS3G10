package ked.pts3g10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Kévin on 16/12/2017.
 */

public class AlertNbCartesInsuffisant extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

            return new AlertDialog.Builder(getActivity())
                    // Set Dialog Icon
                    // Set Dialog Title
                    .setTitle("Alert DialogFragment")
                    // Set Dialog Message
                    .setMessage("Alert DialogFragment Tutorial")

                    // Positive button
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something else
                        }
                    })

                    // Negative Button
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,	int which) {
                            // Do something else
                        }
                    }).create();
        }
    }