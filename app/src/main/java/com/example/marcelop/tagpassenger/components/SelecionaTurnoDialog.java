package com.example.marcelop.tagpassenger.components;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.marcelop.tagpassenger.R;

import java.util.List;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class SelecionaTurnoDialog extends DialogFragment {

    private String[] vetTurno;
    List<Integer> lstSelectItems;
    AlertDialog.Builder builder;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(String selectedItem);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    SelecionaTurnoDialog.NoticeDialogListener mListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (SelecionaTurnoDialog.NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Selecione o Turno desta Viagem")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(R.array.turno_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(String.valueOf(which));
                    }
                });

        return builder.create();
    }

    public String[] getVetTurno() {
        return vetTurno;
    }

    public void setVetTurno(String[] vetTurno) {
        this.vetTurno = vetTurno;
    }


    public List<Integer> getSelectedItems(){
        return lstSelectItems;
    }


}
