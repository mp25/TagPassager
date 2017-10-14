package com.example.marcelop.tagpassenger.dbconnection;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.marcelop.tagpassenger.dao.PassageiroDAO;
import com.example.marcelop.tagpassenger.dao.ViagemDAO;
import com.example.marcelop.tagpassenger.entity.Passageiro;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by Marcelo P on 06/10/2017.
 */

public class TagPassengerDB extends SQLiteOpenHelper {
    static SQLiteDatabase con;
    static TagPassengerDB tpDB;

    public TagPassengerDB(Context context) {
        super(context, "tagPassengerDB", null, 1);

    }

    public static String postDados(String urlUsuario, String parametroUsuario) {
        URL url;
        HttpURLConnection httpURLConnection = null;

        try {
            url = new URL(urlUsuario);
            //httpURLConnection.connect();
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", "" + Integer.toString(parametroUsuario.getBytes().length));
            httpURLConnection.setRequestProperty("Content-Language","pt-BR");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(parametroUsuario);
            dataOutputStream.flush();
            dataOutputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String linha;
            StringBuffer resposta = new StringBuffer();
            while((linha = bufferedReader.readLine()) != null){
                resposta.append(linha);
                resposta.append('\r');
            }
            bufferedReader.close();
            return resposta.toString();

        } catch (Exception e) {
           return e.toString();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptDDL.getCreateTableLocal());
        db.execSQL(ScriptDDL.getCreateTablePassageiro());
        db.execSQL(ScriptDDL.getCreateTableViagemIda());
        db.execSQL(ScriptDDL.getCreateTableViagemVolta());
        db.execSQL(ScriptDDL.getCreateTableTurnoViagem());
        db.execSQL(ScriptDDL.insertLocal());
       // db.execSQL(ScriptDDL.insertTurno());
        db.execSQL(ScriptDDL.insertPassageiros());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public static SQLiteDatabase createConnection(AppCompatActivity obj){

        try {
            tpDB = new TagPassengerDB(obj);
            con = tpDB.getWritableDatabase();
            // con.beginTransaction();
            Toast.makeText(obj, "Conexão Realizada com Sucesso",Toast.LENGTH_SHORT).show();


        }catch (SQLException e){
            Toast.makeText(obj,"Conexão Não realizada",Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }
        return con;
    }
}
