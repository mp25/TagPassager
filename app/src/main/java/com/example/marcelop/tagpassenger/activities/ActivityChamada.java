package com.example.marcelop.tagpassenger.activities;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.dao.PassageiroDAO;
import com.example.marcelop.tagpassenger.dao.TurnoViagemDAO;
import com.example.marcelop.tagpassenger.dao.ViagemDAO;
import com.example.marcelop.tagpassenger.dbconnection.TagPassengerDB;
import com.example.marcelop.tagpassenger.entity.Passageiro;
import com.example.marcelop.tagpassenger.entity.TurnoViagem;
import com.example.marcelop.tagpassenger.entity.Viagem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityChamada extends AppCompatActivity{
    Button fab;
    Button fabConfirma;
    ListView lstPassageiros;
    ArrayAdapter<Passageiro> adapter;
    EditText txtTagTest;
    Button btnConfirmaAlt;

    SQLiteDatabase con;
    PassageiroDAO passageiroDAO;
    ViagemDAO viagemDAO;
    TurnoViagemDAO turnoViagemDAO;

    List<Passageiro> lstDadosPassagTodos;
    List<Passageiro> lstPassageiroEvento = new ArrayList<Passageiro>();
    String selectItemOnDlg;
    Passageiro passageiro;
    TurnoViagem turnoViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectItemOnDlg = getIntent().getExtras().getString("turno");
        fab = (Button) findViewById(R.id.fab);
        fabConfirma = (Button) findViewById(R.id.fabConfirma);
        btnConfirmaAlt = (Button) findViewById(R.id.btnConfirmaAlt);
        lstPassageiros = (ListView) findViewById(R.id.lstPassageiros);
        txtTagTest = (EditText) findViewById(R.id.txtTagTest);
        conectaDB();
        listaTodos();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChamada.this, ActCadPasageiro.class);
                startActivity(intent);
            }
        });

        btnConfirmaAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(txtTagTest != null || !txtTagTest.getText().equals("")){
                   String id = txtTagTest.getText().toString();
                  passageiro = passageiroDAO.consultar(id);

                   if(!passageiro.toString().contains("null - null")){
                       lstPassageiroEvento.add(passageiro);
                           adapter.add(passageiro);
                           lstPassageiros.setAdapter(adapter);
                           listenerClickListView();

                       // adapter = new ArrayAdapter<Passageiro>(ActivityChamada.this,android.R.layout.simple_list_item_1,lstPassageiroEvento);
                       //lstPassageiros = (ListView) findViewById(R.id.lstPassageiros);

                   }else{
                       Toast.makeText(getApplicationContext(), "Não Existe Passageiro para esta TAG",
                               Toast.LENGTH_LONG).show();

                   }

               }
            }
        });


        fabConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lstPassageiroEvento.size() > 0){
                    incluirViagem(lstPassageiroEvento);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Implementar dialog "TEM CERTEZA QUE DESEJA CANCELAR ESTA VIAGEM?"*/
    }

    public void conectaDB(){
        con = TagPassengerDB.createConnection(this);
        viagemDAO = new ViagemDAO(con);
        passageiroDAO = new PassageiroDAO(con);
        turnoViagemDAO = new TurnoViagemDAO(con);
    }

    private void listaTodos(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataHoje = sdf.format(new Date());
        lstDadosPassagTodos = passageiroDAO.listaViagensIda(dataHoje,selectItemOnDlg);
//        turnoViagem = turnoViagemDAO.consultar(Integer.valueOf(selectItemOnDlg));
        adapter = new ArrayAdapter<Passageiro>(this,android.R.layout.simple_list_item_1,lstDadosPassagTodos);
        String[] turno = getResources().getStringArray(R.array.turno_array);

        if(lstDadosPassagTodos.size() > 0){
            lstPassageiros.setAdapter(adapter);
            listenerClickListView();
            Toast.makeText(getApplicationContext(), "Continue a Chamada para o Turno: " + turno[Integer.valueOf(selectItemOnDlg)],Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Não há chamadas para este turno para hoje: " + dataHoje,Toast.LENGTH_LONG).show();
        }
    }


    private void listenerClickListView(){
        lstPassageiros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Posição " + position,
                        Toast.LENGTH_LONG).show();
            }
        });

        lstPassageiros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                passageiro = (Passageiro) adapter.getItem(position);
               // passageiroDAO.excluir(passageiro.getId());
                adapter.remove(passageiro);
                return false;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_novo:

                intent = new Intent(ActivityChamada.this, ActCadPasageiro.class);
                startActivity(intent);
                return false;
}
        return super.onOptionsItemSelected(item);
    }

    public void incluirViagem(List<Passageiro> lstPasViagem){

        for(int i = 0; i<lstPasViagem.size(); i++){
            Viagem viagem = new Viagem();
            Date dataHoje;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            viagem.setDataViagem(formato.format(new Date()));
            viagem.setIdPassageiro(lstPasViagem.get(i).getId());
            viagem.setIdLocal(lstPasViagem.get(i).getIdLocalSaida());
            viagem.setIdTurnoViagem(Integer.valueOf(selectItemOnDlg));
            try {
                viagemDAO.incluir(viagem,false);
                Toast.makeText(this, "Viagem Incluída com SUCESSO",Toast.LENGTH_SHORT).show();
            }catch (SQLException e){
                Toast.makeText(this, "Não foi dessa vez",Toast.LENGTH_SHORT).show();
            }
       }

    }



}
