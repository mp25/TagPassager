package com.example.marcelop.tagpassenger.fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.marcelop.tagpassenger.activities.ActCadPasageiro;
import com.example.marcelop.tagpassenger.R;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class TelaCadastroFragment extends Fragment implements View.OnClickListener{
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.activity_act_cad_pasageiro, container, false);
        view.setOnClickListener(this);
        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSalvar:
                ActCadPasageiro actCadPasageiro = new ActCadPasageiro();
                actCadPasageiro.confirmar();
        }

    }



}
