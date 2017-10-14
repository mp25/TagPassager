package com.example.marcelop.tagpassenger.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcelop.tagpassenger.entity.Passageiro;
import com.example.marcelop.tagpassenger.entity.PassageiroXViagem;
import com.example.marcelop.tagpassenger.entity.Viagem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcelo P on 06/10/2017.
 */

public class ViagemDAO {
    private SQLiteDatabase sqlDB;

    public ViagemDAO(SQLiteDatabase sqlDB){
        this.sqlDB = sqlDB;
    }

    public void incluir(Viagem viagem, boolean isVolta){
        ContentValues contentValues = new ContentValues();
        String table;
        contentValues.put("DATA_VIAGEM",viagem.getDataViagem());
        contentValues.put("ID_PASSAGEIRO",viagem.getIdPassageiro());
        contentValues.put("ID_LOCAL",viagem.getIdLocal());
        if(isVolta){
            contentValues.put("ID_TURNO_VOLTA",viagem.getIdTurnoViagem());
            table = "VIAGEM_VOLTA";
        }else{
            contentValues.put("ID_TURNO_IDA",viagem.getIdTurnoViagem());
            table = "VIAGEM_IDA";
        }
        sqlDB.insertOrThrow(table,null,contentValues);
    }

    public void excluir(int id,boolean isVolta){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        String table;
        if(isVolta){
            table = "VIAGEM_VOLTA";
        }else{
            table = "VIAGEM_IDA";
        }
        sqlDB.delete(table,"ID = ?", params);
    }

    public void alterar(Viagem viagem,boolean isVolta){

        ContentValues contentValues = new ContentValues();
        contentValues.put("DATA_VIAGEM",viagem.getDataViagem());
        contentValues.put("ID_PASSAGEIRO",viagem.getIdPassageiro());
        contentValues.put("ID_LOCAL",viagem.getIdLocal());
        String table;
        if(isVolta){
            contentValues.put("ID_TURNO_VOLTA",viagem.getIdTurnoViagem());
            table = "VIAGEM_VOLTA";
        }else{
            contentValues.put("ID_TURNO_IDA",viagem.getIdTurnoViagem());
            table = "VIAGEM_IDA";
        }


        String[] params = new String[1];
        params[0] = String.valueOf(viagem.getId());
        sqlDB.update(table,contentValues, "ID = ?", params);

    }

    public Viagem consultar(int id, boolean isVolta){
        Viagem viagem = new Viagem();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, DATA_VIAGEM, ID_PASSAGEIRO, ID_LOCAL,");
        if(isVolta){
            sql.append(" ID_TURNO_VOLTA");
            sql.append(" FROM VIAGEM_VOLTA");
        }else{
            sql.append(" ID_TURNO_IDA");
            sql.append(" FROM VIAGEM_IDA");
        }

        sql.append(" WHERE ID = ?");
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = sqlDB.rawQuery(sql.toString(),params);

        if(result.getCount() > 0){
            result.moveToFirst();
            viagem.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
            viagem.setDataViagem(result.getString(result.getColumnIndexOrThrow("DATA_VIAGEM")));
            viagem.setIdPassageiro(result.getInt(result.getColumnIndexOrThrow("ID_PASSAGEIRO")));
            viagem.setIdLocal(result.getInt(result.getColumnIndexOrThrow("ID_LOCAL")));
            if(isVolta){
                viagem.setIdTurnoViagem(result.getInt(result.getColumnIndexOrThrow("ID_TURNO_VOLTA")));
            }else {
                viagem.setIdTurnoViagem(result.getInt(result.getColumnIndexOrThrow("ID_TURNO_IDA")));
            }
        }

        return viagem;
    }

    public List<Passageiro> validaListaPassageiros(String data,String turno){
        List<Passageiro> lstPassageiros = new ArrayList<Passageiro>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.ID, p.NOME_PASSAGEIRO, p.TAG_PASSAGEIRO, v.DATA_VIAGEM FROM PASSAGEIRO p");

        sql.append(" JOIN VIAGEM_IDA v");
        /*sql.append(" JOIN VIAGEM_IDA v");*/
        sql.append(" ON p.ID = v.ID_PASSAGEIRO ");
        sql.append(" WHERE DATA_VIAGEM = ?");
        sql.append(" AND ID_TURNO_IDA = ?");


//        sql.append(" WHERE id in (");
        //       sql.append("SELECT ID_PASSAGEIRO FROM VIAGEM_IDA WHERE DATA_VIAGEM = ?)");
        String[] params = new String[2];
        params[0] = data;
        params[1] = turno;
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
    }



    public List<Passageiro> listaViagens(String data,String turno, boolean isVolta){
        List<Passageiro> lstPassageiros = new ArrayList<Passageiro>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.ID, p.NOME_PASSAGEIRO, p.TAG_PASSAGEIRO, v.DATA_VIAGEM FROM PASSAGEIRO p");
        if(isVolta){
            sql.append(" JOIN VIAGEM_VOLTA v");
        }else{
            sql.append(" JOIN VIAGEM_IDA v");
        }
        /*sql.append(" JOIN VIAGEM_IDA v");*/
        sql.append(" ON p.ID = v.ID_PASSAGEIRO ");
        sql.append(" WHERE DATA_VIAGEM = ?");
        if(isVolta){
            sql.append(" AND ID_TURNO_VOLTA = ?");
        }else{
            sql.append(" AND ID_TURNO_IDA = ?");
        }

//        sql.append(" WHERE id in (");
 //       sql.append("SELECT ID_PASSAGEIRO FROM VIAGEM_IDA WHERE DATA_VIAGEM = ?)");
        String[] params = new String[2];
        params[0] = data;
        params[1] = turno;
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
    }

    public List<Viagem> consultarTodos(boolean isVolta){
        List<Viagem> lstViagem = new ArrayList<Viagem>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, DATA_VIAGEM, ID_PASSAGEIRO, ID_LOCAL,");

        if(isVolta){
            sql.append(" ID_TURNO_VOLTA");
            sql.append(" FROM VIAGEM_VOLTA");
        }else{
            sql.append(" ID_TURNO_IDA");
            sql.append(" FROM VIAGEM_IDA");
        }

        Cursor result = sqlDB.rawQuery(sql.toString(),null);

        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                Viagem viagem = new Viagem();
                viagem.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                viagem.setDataViagem(result.getString(result.getColumnIndexOrThrow("DATA_VIAGEM")));
                viagem.setIdPassageiro(result.getInt(result.getColumnIndexOrThrow("ID_PASSAGEIRO")));
                viagem.setIdLocal(result.getInt(result.getColumnIndexOrThrow("ID_LOCAL")));
                if(isVolta){
                    viagem.setIdTurnoViagem(result.getInt(result.getColumnIndexOrThrow("ID_TURNO_VOLTA")));
                }else{
                    viagem.setIdTurnoViagem(result.getInt(result.getColumnIndexOrThrow("ID_TURNO_IDA")));
                }

                lstViagem.add(viagem);
            }while(result.moveToNext());
        }
        return lstViagem;
    }



}
