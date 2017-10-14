package com.example.marcelop.tagpassenger.dbconnection;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.marcelop.tagpassenger.entity.Local;

/**
 * Created by Marcelo P on 06/10/2017.
 */

public class ScriptDDL {

    private static SQLiteDatabase sqlDB;
    public static String getCreateTableViagemIda(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE VIAGEM_IDA(\n" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,\n" +
                "DATA_VIAGEM DATE NOT NULL,\n" +
                "ID_PASSAGEIRO INTEGER CONSTRAINT fk_passageiros REFERENCES PASSAGEIROS (ID),\n" +
                "ID_TURNO_IDA INTEGER CONSTRAINT fk_viagem_ida REFERENCES TURNO_VIAGEM (ID),\n" +
                "ID_LOCAL INTEGER CONSTRAINT fk_viagem_saida_local REFERENCES LOCAL (ID),\n" +
                "FOREIGN KEY (ID_LOCAL) REFERENCES LOCAL (ID),\n" +
                "FOREIGN KEY (ID_TURNO_IDA) REFERENCES TURNO_VIAGEM (ID),\n" +
                "FOREIGN KEY (ID_PASSAGEIRO) REFERENCES PASSAGEIRO(ID));");


        return sql.toString();
    }

    public static String getCreateTableViagemVolta(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE VIAGEM_VOLTA(\n" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,\n" +
                "DATA_VIAGEM DATE NOT NULL,\n" +
                "ID_PASSAGEIRO INTEGER CONSTRAINT fk_passageiros REFERENCES PASSAGEIROS (ID),\n" +
                "ID_TURNO_VOLTA INTEGER CONSTRAINT fk_viagem_volta REFERENCES TURNO_VIAGEM (ID),\n" +
                "ID_LOCAL INTEGER CONSTRAINT fk_viagem_saida_local REFERENCES LOCAL (ID),\n" +
                "FOREIGN KEY (ID_LOCAL) REFERENCES LOCAL (ID),\n" +
                "FOREIGN KEY (ID_TURNO_VOLTA) REFERENCES TURNO_VIAGEM (ID),\n" +
                "FOREIGN KEY (ID_PASSAGEIRO) REFERENCES PASSAGEIRO(ID));");

        return sql.toString();
    }

    public static String getCreateTableLocal(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE LOCAL(\n" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,\n" +
                "NOME_LOCAL VARCHAR(100) NOT NULL);");
        return sql.toString();
    }


    public static String getCreateTableTurnoViagem(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE TURNO_VIAGEM(\n" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,\n" +
                "NOME_TURNO VARCHAR(100) NOT NULL);");
        return sql.toString();
    }

    public static String insertLocal(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO LOCAL VALUES");
        sql.append(" ('1', 'Campinho'),");
        sql.append(" ('2', 'Marechal'),");
        sql.append(" ('3', 'St Isabel');");
        return sql.toString();
    }

    public static String insertPassageiros(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO PASSAGEIRO VALUES");
        sql.append(" (NULL, 'Marcelo Pimentel Silva','3142643','12852576775','1234','1','1'),");
        sql.append(" (NULL, 'Luiza de Paiva','4432215','65412145484','2345','1','2'),");
        sql.append(" (NULL, 'Mariana Pimentel Silva','741245','84561245784','3456','1','1'),");
        sql.append(" (NULL, 'Luzia Vieira Pimentel','2123545','32151246858','4567','1','2'),");
        sql.append(" (NULL, 'Morgana de Paiva','3321544','12415845124','5678','2','2'),");
        sql.append(" (NULL, 'Eleonor de Paiva','1451555','84567125496','6789','2','1'),");
        sql.append(" (NULL, 'Aldo Vicente da Silva','554121','6524614548','7890','1','1');");
        return sql.toString();
    }


    public static String insertTurno(){
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO TURNO_VIAGEM VALUES");
        sql.append(" (NULL, 'Matutino'),");
        sql.append(" (NULL, 'Verspertino'),");
        sql.append(" (NULL, 'Noturno');");
        return sql.toString();
    }


    public static String getCreateTablePassageiro(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE PASSAGEIRO(\n" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL,\n" +
                "NOME_PASSAGEIRO VARCHAR(40) NOT NULL,\n" +
                "RG_PASSAGEIRO VARCHAR(10),\n" +
                "CPF_PASSAGEIRO VARCHAR(15),\n" +
                "TAG_PASSAGEIRO VARCHAR(30),\n" +
                "ID_LOCAL_ENTRADA INTEGER CONSTRAINT fk_local_entrada REFERENCES LOCAL (ID),\n" +
                "ID_LOCAL_SAIDA INTEGER CONSTRAINT fk_local_saida REFERENCES LOCAL (ID),\n" +
                "FOREIGN KEY (ID_LOCAL_SAIDA) REFERENCES LOCAL (ID),\n" +
                "FOREIGN KEY (ID_LOCAL_ENTRADA) REFERENCES LOCAL (ID));");
        return sql.toString();
    }
}
