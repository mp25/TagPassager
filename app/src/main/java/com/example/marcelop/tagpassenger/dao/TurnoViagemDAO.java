package com.example.marcelop.tagpassenger.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcelop.tagpassenger.entity.Passageiro;
import com.example.marcelop.tagpassenger.entity.TurnoViagem;
import com.example.marcelop.tagpassenger.entity.Viagem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcelo P on 13/10/2017.
 */

public class TurnoViagemDAO {

    private SQLiteDatabase sqlDB;

    public TurnoViagemDAO(SQLiteDatabase sqlDB){
        this.sqlDB = sqlDB;
    }

    public void incluir(TurnoViagem turnoViagem){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME_TURNO",turnoViagem.getNomeTurno());
        sqlDB.insertOrThrow("TURNO_VIAGEM",null,contentValues);
    }

    public void excluir(int id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        sqlDB.delete("TURNO_VIAGEM","ID = ?", params);
    }

    public void alterar(TurnoViagem turnoViagem){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME_TURNO",turnoViagem.getNomeTurno());

        String[] params = new String[1];
        params[0] = String.valueOf(turnoViagem.getId());
        sqlDB.update("TURNO_VIAGEM",contentValues, "ID = ?", params);

    }

    public TurnoViagem consultar(int id){
        TurnoViagem turnoViagem = new TurnoViagem();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, NOME_TURNO");
        sql.append(" FROM TURNO_VIAGEM");


        sql.append(" WHERE ID = ?");
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = sqlDB.rawQuery(sql.toString(),params);

        if(result.getCount() > 0){
            result.moveToFirst();
            turnoViagem.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
            turnoViagem.setNomeTurno(result.getString(result.getColumnIndexOrThrow("NOME_TURNO")));
        }

        return turnoViagem;
    }


    /*public List<Passageiro> listaTurno(String data){
        List<Passageiro> lstPassageiros = new ArrayList<Passageiro>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.ID, p.NOME_PASSAGEIRO, p.TAG_PASSAGEIRO, v.DATA_VIAGEM FROM PASSAGEIRO p");
        sql.append(" JOIN VIAGEM_IDA v");
        sql.append(" ON p.ID = v.ID_PASSAGEIRO ");
        sql.append(" WHERE DATA_VIAGEM = ?");
//        sql.append(" WHERE id in (");
        //       sql.append("SELECT ID_PASSAGEIRO FROM VIAGEM_IDA WHERE DATA_VIAGEM = ?)");
        String[] params = new String[1];
        params[0] = data;
        Cursor result = sqlDB.rawQuery(sql.toString(),params);

        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                Passageiro Passageiro = new Passageiro();

                Passageiro.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                Passageiro.setNomePassageiro(result.getString(result.getColumnIndexOrThrow("NOME_PASSAGEIRO")));
                Passageiro.setTagPassageiro(result.getString(result.getColumnIndexOrThrow("TAG_PASSAGEIRO")));
                //PassageiroXViagem.setDataViagem(result.getString(result.getColumnIndexOrThrow("DATA_VIAGEM")));

                lstPassageiros.add(Passageiro);



            }while(result.moveToNext());
        }

        return lstPassageiros;
    }*/

    public List<TurnoViagem> consultarTodos(){
        List<TurnoViagem> lstTurno = new ArrayList<TurnoViagem>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, TURNO_VIAGEM");
        sql.append(" FROM TURNO_VIAGEM");

        Cursor result = sqlDB.rawQuery(sql.toString(),null);

        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                TurnoViagem turnoViagem = new TurnoViagem();
                turnoViagem.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                turnoViagem.setNomeTurno(result.getString(result.getColumnIndexOrThrow("NOME_TURNO")));

                lstTurno.add(turnoViagem);
            }while(result.moveToNext());
        }
        return lstTurno;
    }


}
