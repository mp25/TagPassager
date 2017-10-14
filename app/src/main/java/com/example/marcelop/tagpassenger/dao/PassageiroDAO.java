package com.example.marcelop.tagpassenger.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcelop.tagpassenger.entity.Passageiro;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Marcelo P on 06/10/2017.
 */

public class PassageiroDAO{

    private SQLiteDatabase sqlDB;

    public PassageiroDAO(SQLiteDatabase sqlDB){
        this.sqlDB = sqlDB;
    }

    public void incluir(Passageiro passageiro){
        ContentValues contentValues = new ContentValues();

        contentValues.put("NOME_PASSAGEIRO",passageiro.getNomePassageiro());
        contentValues.put("RG_PASSAGEIRO",passageiro.getRg());
        contentValues.put("CPF_PASSAGEIRO",passageiro.getCpf());
        contentValues.put("ID_LOCAL_ENTRADA",passageiro.getIdLocalEntrada());
        contentValues.put("ID_LOCAL_SAIDA",passageiro.getIdLocalSaida());
        contentValues.put("TAG_PASSAGEIRO",passageiro.getTagPassageiro());
        sqlDB.insertOrThrow("PASSAGEIRO",null,contentValues);
    }

    public void excluir(int id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        sqlDB.delete("PASSAGEIRO","ID = ?", params);
    }

    public void alterar(Passageiro passageiro){

        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME_PASSAGEIRO",passageiro.getNomePassageiro());
        contentValues.put("RG_PASSAGEIRO",passageiro.getRg());
        contentValues.put("CPF_PASSAGEIRO",passageiro.getCpf());
        contentValues.put("ID_LOCAL_ENTRADA",passageiro.getIdLocalEntrada());
        contentValues.put("ID_LOCAL_SAIDA",passageiro.getIdLocalSaida());
        contentValues.put("TAG_PASSAGEIRO",passageiro.getTagPassageiro());

        String[] params = new String[1];
        params[0] = String.valueOf(passageiro.getId());
        sqlDB.update("PASSAGEIRO",contentValues, "ID = ?", params);

    }

    public Passageiro consultar(String id){
        Passageiro passageiro = new Passageiro();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, NOME_PASSAGEIRO, RG_PASSAGEIRO, CPF_PASSAGEIRO, TAG_PASSAGEIRO, ID_LOCAL_ENTRADA, ID_LOCAL_SAIDA");
        sql.append(" FROM PASSAGEIRO");
        sql.append(" WHERE TAG_PASSAGEIRO = ?");
        String[] params = new String[1];
        params[0] = id;
       Cursor result = sqlDB.rawQuery(sql.toString(),params);

        if(result.getCount() > 0){
            result.moveToFirst();
            passageiro.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
            passageiro.setNomePassageiro(result.getString(result.getColumnIndexOrThrow("NOME_PASSAGEIRO")));
            passageiro.setRg(result.getString(result.getColumnIndexOrThrow("RG_PASSAGEIRO")));
            passageiro.setCpf(result.getString(result.getColumnIndexOrThrow("CPF_PASSAGEIRO")));
            passageiro.setTagPassageiro(result.getString(result.getColumnIndexOrThrow("TAG_PASSAGEIRO")));
            passageiro.setIdLocalEntrada(result.getInt(result.getColumnIndexOrThrow("ID_LOCAL_ENTRADA")));
            passageiro.setIdLocalSaida(result.getInt(result.getColumnIndexOrThrow("ID_LOCAL_SAIDA")));
        }

        return passageiro;
    }

    public List<Passageiro> listaViagensIda(String data, String turno){
        List<Passageiro> lstPassageiros = new ArrayList<Passageiro>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, NOME_PASSAGEIRO, TAG_PASSAGEIRO FROM PASSAGEIRO p");
//        sql.append(" JOIN VIAGEM_IDA v");
//        sql.append(" ON p.ID = v.ID_PASSAGEIRO ");
        sql.append(" WHERE id in (");
        sql.append("SELECT ID_PASSAGEIRO FROM VIAGEM_IDA WHERE DATA_VIAGEM = ?");
        sql.append("AND ID_TURNO_IDA = ?)");
        String[] params = new String[2];
        params[0] = data;
        params[1] = turno;
        Cursor result = sqlDB.rawQuery(sql.toString(),params);



        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                Passageiro passageiro = new Passageiro();

                passageiro.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                passageiro.setNomePassageiro(result.getString(result.getColumnIndexOrThrow("NOME_PASSAGEIRO")));
                passageiro.setTagPassageiro(result.getString(result.getColumnIndexOrThrow("TAG_PASSAGEIRO")));

                lstPassageiros.add(passageiro);



            }while(result.moveToNext());
        }



        return lstPassageiros;
    }

    public List<Passageiro> listaViagensVolta(String data, String turno){
        List<Passageiro> lstPassageiros = new ArrayList<Passageiro>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, NOME_PASSAGEIRO, TAG_PASSAGEIRO FROM PASSAGEIRO p");
//        sql.append(" JOIN VIAGEM_IDA v");
//        sql.append(" ON p.ID = v.ID_PASSAGEIRO ");
        sql.append(" WHERE id in (");
        sql.append("SELECT ID_PASSAGEIRO FROM VIAGEM_VOLTA WHERE DATA_VIAGEM = ?");
        sql.append("AND ID_TURNO_VOLTA = ?)");
        String[] params = new String[2];
        params[0] = data;
        params[1] = turno;
        Cursor result = sqlDB.rawQuery(sql.toString(),params);



        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                Passageiro passageiro = new Passageiro();

                passageiro.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                passageiro.setNomePassageiro(result.getString(result.getColumnIndexOrThrow("NOME_PASSAGEIRO")));
                passageiro.setTagPassageiro(result.getString(result.getColumnIndexOrThrow("TAG_PASSAGEIRO")));

                lstPassageiros.add(passageiro);



            }while(result.moveToNext());
        }



        return lstPassageiros;
    }

    public List<Passageiro> consultarTodos(){
        List<Passageiro> lstPassageiros = new ArrayList<Passageiro>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, NOME_PASSAGEIRO, RG_PASSAGEIRO, CPF_PASSAGEIRO, TAG_PASSAGEIRO, ID_LOCAL_ENTRADA, ID_LOCAL_SAIDA " );
        sql.append("FROM PASSAGEIRO");

        Cursor result = sqlDB.rawQuery(sql.toString(),null);

        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                Passageiro passageiro = new Passageiro();

                passageiro.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                passageiro.setNomePassageiro(result.getString(result.getColumnIndexOrThrow("NOME_PASSAGEIRO")));
                passageiro.setRg(result.getString(result.getColumnIndexOrThrow("RG_PASSAGEIRO")));
                passageiro.setCpf(result.getString(result.getColumnIndexOrThrow("CPF_PASSAGEIRO")));
                passageiro.setTagPassageiro(result.getString(result.getColumnIndexOrThrow("TAG_PASSAGEIRO")));
                passageiro.setIdLocalEntrada(result.getInt(result.getColumnIndexOrThrow("ID_LOCAL_ENTRADA")));
                passageiro.setIdLocalSaida(result.getInt(result.getColumnIndexOrThrow("ID_LOCAL_SAIDA")));

                lstPassageiros.add(passageiro);



            }while(result.moveToNext());
        }



        return lstPassageiros;
    }

}
