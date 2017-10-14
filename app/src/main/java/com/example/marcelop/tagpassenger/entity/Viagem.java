package com.example.marcelop.tagpassenger.entity;

import java.util.Date;

/**
 * Created by Marcelo P on 10/10/2017.
 */

public class Viagem {

    private int id;
    private String dataViagem;
    private int idPassageiro;
    private int idLocal;
    private int idTurnoViagem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataViagem() {
        return dataViagem;
    }

    public void setDataViagem(String dataViagem) {
        this.dataViagem = dataViagem;
    }

    public int getIdPassageiro() {
        return idPassageiro;
    }

    public void setIdPassageiro(int idPassageiro) {
        this.idPassageiro = idPassageiro;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdTurnoViagem() {
        return idTurnoViagem;
    }

    public void setIdTurnoViagem(int idTurnoViagem) {
        this.idTurnoViagem = idTurnoViagem;
    }
}
