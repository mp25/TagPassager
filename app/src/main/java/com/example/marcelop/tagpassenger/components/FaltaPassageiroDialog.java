package com.example.marcelop.tagpassenger.components;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.marcelop.tagpassenger.entity.Passageiro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class FaltaPassageiroDialog extends DialogFragment {

    private String[] vetPassageiros;
    List<Integer> lstSelectItems;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(List<Integer> lstSelectedItems);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    NoticeDialogListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        lstSelectItems = new ArrayList();
        // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Marque os passageiros que não voltarão")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(getVetPassageiros(), null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    lstSelectItems.add(which);
                                } else if (lstSelectItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    lstSelectItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(lstSelectItems);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public String[] getVetPassageiros() {
        return vetPassageiros;
    }

    public void setVetPassageiros(String[] vetPassageiros) {
        this.vetPassageiros = vetPassageiros;
    }


    public List<Integer> getSelectedItems(){
        return lstSelectItems;
    }
}
