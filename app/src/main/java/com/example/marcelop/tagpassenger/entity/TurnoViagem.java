package com.example.marcelop.tagpassenger.entity;

/**
 * Created by Marcelo P on 13/10/2017.
 */

public class TurnoViagem {

    private int id;
    private String nomeTurno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTurno() {
        return nomeTurno;
    }

    public void setNomeTurno(String nomeTurno) {
        this.nomeTurno = nomeTurno;
    }

    @Override
    public String toString() {
        return nomeTurno;
    }
}
