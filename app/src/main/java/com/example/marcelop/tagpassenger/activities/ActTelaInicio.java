package com.example.marcelop.tagpassenger.activities;

import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.components.SelecionaTurnoDialog;

public class ActTelaInicio extends AppCompatActivity implements SelecionaTurnoDialog.NoticeDialogListener{

    Button btnValidar;
    Button btnChamada;
    Button btnCadastrar;
    Button btnListaViagemIda;
    Button btnListaViagemVolta;
    Intent intent;
    BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tela_inicio);
        btnValidar = (Button) findViewById(R.id.btnValidar);
        btnChamada = (Button) findViewById(R.id.btnChamada);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnListaViagemIda = (Button) findViewById(R.id.btnListaViagemIda);
        btnListaViagemVolta = (Button) findViewById(R.id.btnListaViagemVolta);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null){

        }else{
            Intent enableBlueTooth= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBlueTooth,1);
        }

        btnChamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),ActivityChamada.class);
                ShowTurnoDialog();
            }
        });


        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),ActValidaPassageiros.class);
                ShowTurnoDialog();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ActTelaInicio.this,ActCadPasageiro.class);
                startActivity(intent);
            }
        });

        btnListaViagemIda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ActTelaInicio.this,ActViagemIda.class);
                ShowTurnoDialog();
                /*startActivity(intent);*/
            }
        });

        btnListaViagemVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ActTelaInicio.this,ActViagemVolta.class);
                ShowTurnoDialog();
                /*startActivity(intent);*/
            }
        });

    }

    public void ShowTurnoDialog(){
        SelecionaTurnoDialog turnoDialog = new SelecionaTurnoDialog();
        turnoDialog.show(getFragmentManager(),"missiles");
    }

    @Override
    public void onDialogPositiveClick(String selectedItem) {
        intent.putExtra("turno",selectedItem);
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

}
