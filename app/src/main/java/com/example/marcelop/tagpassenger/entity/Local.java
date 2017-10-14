package com.example.marcelop.tagpassenger.entity;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class Local {

    private int id;
    private String nomeLocal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    @Override
    public String toString() {
        return nomeLocal;
    }
}
