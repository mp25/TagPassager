package com.example.marcelop.tagpassenger.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcelop.tagpassenger.entity.Local;
import com.example.marcelop.tagpassenger.entity.Viagem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class LocalDAO {
    private SQLiteDatabase sqlDB;

    public LocalDAO(SQLiteDatabase sqlDB){
        this.sqlDB = sqlDB;
    }

    public List<Local> consultarTodos(){
        List<Local> lstLocal = new ArrayList<Local>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID, NOME_LOCAL FROM LOCAL");


        Cursor result = sqlDB.rawQuery(sql.toString(),null);

        if(result.getCount() > 0){
            result.moveToFirst();

            do{
                Local local = new Local();
                local.setId(result.getInt(result.getColumnIndexOrThrow("ID")));
                local.setNomeLocal(result.getString(result.getColumnIndexOrThrow("NOME_LOCAL")));
                lstLocal.add(local);
            }while(result.moveToNext());
        }
        return lstLocal;
    }
}
