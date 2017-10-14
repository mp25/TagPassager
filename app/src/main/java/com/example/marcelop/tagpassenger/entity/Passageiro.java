package com.example.marcelop.tagpassenger.entity;

/**
 * Created by Marcelo P on 06/10/2017.
 */

public class Passageiro {

    private int id;
    private String nomePassageiro;
    private String rg;
    private String cpf;
    private int idLocalEntrada;
    private int idLocalSaida;
    private String tagPassageiro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePassageiro() {
        return nomePassageiro;
    }

    public void setNomePassageiro(String nomePassageiro) {
        this.nomePassageiro = nomePassageiro;
    }

    public String getTagPassageiro() {
        return tagPassageiro;
    }

    public void setTagPassageiro(String tagPassageiro) {
        this.tagPassageiro = tagPassageiro;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdLocalEntrada() {
        return idLocalEntrada;
    }

    public void setIdLocalEntrada(int idLocalEntrada) {
        this.idLocalEntrada = idLocalEntrada;
    }

    public int getIdLocalSaida() {
        return idLocalSaida;
    }

    public void setIdLocalSaida(int idLocalSaida) {
        this.idLocalSaida = idLocalSaida;
    }

    @Override
    public String toString() {
        return id + " - " + nomePassageiro + " - " + tagPassageiro;
    }
}
