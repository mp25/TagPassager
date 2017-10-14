package com.example.marcelop.tagpassenger.activities;

import android.app.DialogFragment;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.components.FaltaPassageiroDialog;
import com.example.marcelop.tagpassenger.dao.PassageiroDAO;
import com.example.marcelop.tagpassenger.dao.ViagemDAO;
import com.example.marcelop.tagpassenger.dbconnection.TagPassengerDB;
import com.example.marcelop.tagpassenger.entity.Passageiro;
import com.example.marcelop.tagpassenger.entity.PassageiroXViagem;
import com.example.marcelop.tagpassenger.entity.Viagem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActValidaPassageiros extends AppCompatActivity implements FaltaPassageiroDialog.NoticeDialogListener {

    SQLiteDatabase con;
    Button btnAdd;
    EditText txtValidaTag;
    ListView lstValidaPassageiros;
    Button fabValidaLst;
    List<Passageiro> lstPassagPresentesIda;
    List<Passageiro> lstPassagPresentesVolta = new ArrayList<>();
    ArrayAdapter<Passageiro> adapter;
    ViagemDAO viagemDAO;
    PassageiroDAO passageiroDAO;
    Passageiro passageiro;
    String selectItemOnDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectItemOnDlg = getIntent().getExtras().getString("turno");
        setContentView(R.layout.activity_act_valida_passageiros);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createConnection();
        btnAdd = (Button) findViewById(R.id.btnAdd);
        txtValidaTag = (EditText) findViewById(R.id.txtValidaTag);
        lstValidaPassageiros = (ListView) findViewById(R.id.lstValidaPassageiros);
        fabValidaLst = (Button) findViewById(R.id.fabValidaLst);
        listaTodos();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = txtValidaTag.getText().toString();
                Passageiro passageiro = new Passageiro();
                for(int i = 0; i < lstPassagPresentesIda.size(); i++){
                    passageiro = lstPassagPresentesIda.get(i);
                    if(passageiro.getTagPassageiro().equals(tag)){
                        lstPassagPresentesVolta.add(passageiro);
                        adapter.remove(passageiro);
                        i = 0;
                    }
                }
            }
        });

        fabValidaLst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(lstPassagPresentesIda.size() > 0){
               showNoticeDialog();
            }else{
                incluirViagem(lstPassagPresentesVolta);
            }
            }
        });

    }

    private String[] getPassageiroFaltam(){
        String[] lstPassageiros = new String[lstPassagPresentesIda.size()];
        for(int i = 0; i < lstPassagPresentesIda.size(); i++){
            lstPassageiros[i] = lstPassagPresentesIda.get(i).getNomePassageiro();
        }
        return lstPassageiros;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public void incluirViagem(List<Passageiro> lstPasViagem) {

        for (int i = 0; i < lstPasViagem.size(); i++) {
            Viagem viagem = new Viagem();
            Date dataHoje;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            viagem.setDataViagem(formato.format(new Date()));
            viagem.setIdPassageiro(lstPasViagem.get(i).getId());
            viagem.setIdLocal(lstPasViagem.get(i).getIdLocalSaida());
            viagem.setIdTurnoViagem(Integer.valueOf(selectItemOnDlg));
            try {
                viagemDAO.incluir(viagem, true);
                Toast.makeText(this, "Viagem Incluída com SUCESSO", Toast.LENGTH_SHORT).show();
                finish();
            } catch (SQLException e) {
                Toast.makeText(this, "Não foi dessa vez", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private void listaTodos(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoje = sdf.format(new Date());
        lstPassagPresentesIda = viagemDAO.validaListaPassageiros(dataHoje,selectItemOnDlg);/*Parametro TURNO e Se é viagem IDA ou VOLTA*/
        adapter = new ArrayAdapter<Passageiro>(this,android.R.layout.simple_list_item_1, lstPassagPresentesIda);
        lstValidaPassageiros.setAdapter(adapter);
        //listenerClickListView();
    }



    private void createConnection(){
        con = TagPassengerDB.createConnection(this);
        viagemDAO = new ViagemDAO(con);
        passageiroDAO = new PassageiroDAO(con);
    }
    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        FaltaPassageiroDialog newFrag = new FaltaPassageiroDialog();
        newFrag.setVetPassageiros(getPassageiroFaltam());
        newFrag.show(getFragmentManager(), "missiles");
    }

    @Override
    public void onDialogPositiveClick(List<Integer> lstSelectedItems) {
        for(int i = 0; i<lstSelectedItems.size();i++){
            passageiro = adapter.getItem(lstSelectedItems.get(i));
            adapter.remove(passageiro);
            //Resolver problema de deletar da lista
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
