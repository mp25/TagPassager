package com.example.marcelop.tagpassenger.activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.dao.LocalDAO;
import com.example.marcelop.tagpassenger.dao.PassageiroDAO;
import com.example.marcelop.tagpassenger.dbconnection.ServiceHandler;
import com.example.marcelop.tagpassenger.dbconnection.TagPassengerDB;
import com.example.marcelop.tagpassenger.entity.Local;
import com.example.marcelop.tagpassenger.entity.Passageiro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActCadPasageiro extends AppCompatActivity {

    PassageiroDAO passageiroDAO;
    LocalDAO localDAO;
    Button btnSalvar;
    EditText txtBoxNome;
    EditText txtBoxTag;
    EditText txtBoxRg;
    EditText txtBoxCpf;
    Spinner spinLocalEnt;
    Spinner spinLocalSaida;
    SQLiteDatabase con;
    Passageiro passageiro;
    String url = "";
    String parametros = "";
    List<Local> lstLocal;
    Local localEntrada;
    Local localSaida;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cad_pasageiro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createConnection();
        lstLocal = new ArrayList<>();
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        txtBoxNome = (EditText) findViewById(R.id.txtBoxNome);
        txtBoxRg = (EditText) findViewById(R.id.txtBoxRg);
        txtBoxCpf = (EditText) findViewById(R.id.txtBoxCpf);
        txtBoxTag = (EditText) findViewById(R.id.txtBoxTag);
        spinLocalEnt = (Spinner) findViewById(R.id.spinLocalEnt);
        spinLocalSaida = (Spinner) findViewById(R.id.spinLocalSaida);

        /*Se houver conexão com a internet busca dados do servidor*/
/*        GetLocal getLocal = new GetLocal();
        String status = getLocal.execute().getStatus().toString();*/
        /*Se não houver conexão insere dados no banco local do celular*/
        //localDAO.incluir();
        lstLocal = localDAO.consultarTodos();
        populateSpinner();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmar();
                //persisteRegistroServer();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void persisteRegistroServer(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            PersisteDados persisteDados = new PersisteDados();
            String nome = txtBoxNome.getText().toString() ;
            String cpf = txtBoxCpf.getText().toString();
            String rg = txtBoxRg.getText().toString();
            String tag = txtBoxTag.getText().toString();
            int idLocalE = ((Local)spinLocalEnt.getSelectedItem()).getId();
            int idLocalS = ((Local)spinLocalEnt.getSelectedItem()).getId();

            url = "http://192.168.1.102:80/tcc/cadastro.php";

            parametros = "nome="+nome + "&rg=" +rg + "&cpf=" + cpf + "&tag="+tag + "&idLocalE="+String.valueOf(idLocalE) + "&idLocalS=" + String.valueOf(idLocalS);
            persisteDados.execute(url);
                    /*confirmar();*/
        }else{
            Toast.makeText(getApplicationContext(), "Sem Conexão Com a Internet",Toast.LENGTH_SHORT).show();
        }


    }


    public void confirmar(){
        passageiro = new Passageiro();
        passageiro.setNomePassageiro(txtBoxNome.getText().toString());
        passageiro.setRg(txtBoxRg.getText().toString());
        passageiro.setCpf(txtBoxCpf.getText().toString());
        passageiro.setTagPassageiro(txtBoxTag.getText().toString());
        localEntrada = (Local) spinLocalEnt.getSelectedItem();
        passageiro.setIdLocalEntrada(localEntrada.getId());
        localSaida = (Local) spinLocalSaida.getSelectedItem();
        passageiro.setIdLocalSaida(localSaida.getId());
        try {

            passageiroDAO.incluir(passageiro);
            Toast.makeText(this, R.string.Message,Toast.LENGTH_SHORT).show();
            finish();
        }catch (SQLException e){
            Toast.makeText(this, "Não foi dessa vez",Toast.LENGTH_SHORT).show();
        }
    }

    private void createConnection(){
        con = TagPassengerDB.createConnection(this);
        passageiroDAO = new PassageiroDAO(con);
        localDAO = new LocalDAO(con);
    }

    private class PersisteDados extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
           return TagPassengerDB.postDados(urls[0],parametros);
        }

        @Override
        protected void onPostExecute(String s) {
            txtBoxTag.setText(s);
        }
    }

    private class GetLocal extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall("http://192.168.1.102:80/tcc/local.php", ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("LOCAL");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Local local = new Local();
                            local.setId(catObj.getInt("ID"));
                            local.setNomeLocal(catObj.getString("NOME_LOCAL"));
                            lstLocal.add(local);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            populateSpinner();
        }
    }

    private void populateSpinner() {
        List<Local> lables = new ArrayList<Local>();

        //txtCategory.setText("");

        for (int i = 0; i < lstLocal.size(); i++) {
            lables.add(lstLocal.get(i));
        }

        // Creating adapter for spinner
        ArrayAdapter<Local> spinnerAdapter = new ArrayAdapter<Local>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinLocalEnt.setAdapter(spinnerAdapter);
        spinLocalSaida.setAdapter(spinnerAdapter);
    }

}
