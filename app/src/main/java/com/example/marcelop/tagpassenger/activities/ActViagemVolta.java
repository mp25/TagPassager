package com.example.marcelop.tagpassenger.activities;

import android.app.DatePickerDialog;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.dao.PassageiroDAO;
import com.example.marcelop.tagpassenger.dbconnection.TagPassengerDB;
import com.example.marcelop.tagpassenger.entity.Passageiro;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Marcelo P on 13/10/2017.
 */

public class ActViagemVolta extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    SQLiteDatabase con;
    PassageiroDAO passageiroDAO;
    ListView lstViagem;
    ArrayAdapter<Passageiro> adapter;
    EditText txtDate;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    String selectItemOnDlg;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_viagem_volta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectItemOnDlg = getIntent().getExtras().getString("turno");
        lstViagem = (ListView) findViewById(R.id.lst_viagem_volta);
        txtDate = (EditText)findViewById(R.id.txtDataVolta);
        createConnection();

        myCalendar= Calendar.getInstance();

        date= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                listarViagens();
            }

        };

        datePickerDialog =  new DatePickerDialog(ActViagemVolta.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        /*fabCarregarLstViagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarViagens();
            }
        });*/

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void listarViagens(){
        final List<Passageiro> lstDadosPassag = passageiroDAO.listaViagensVolta(txtDate.getText().toString(),selectItemOnDlg);
        if(lstDadosPassag.size() > 0){
            adapter = new ArrayAdapter<Passageiro>(this,android.R.layout.simple_list_item_1,lstDadosPassag);
            lstViagem.setAdapter(adapter);
        }else{
            if(adapter != null)
                adapter.clear();
            Toast.makeText(this, "Não há dados disponíveis para esta Data",Toast.LENGTH_SHORT).show();

        }

    }

    private void createConnection(){
        con = TagPassengerDB.createConnection(this);
        passageiroDAO = new PassageiroDAO(con);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String data = dayOfMonth+"/"+month+"/"+year;
        txtDate.setText(data);
        listarViagens();
        datePickerDialog.hide();


    }


    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        txtDate.setText(sdf.format(myCalendar.getTime()));
    }


}
