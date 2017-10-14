package com.example.marcelop.tagpassenger.entity;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class PassageiroXViagem extends Passageiro {

    private Viagem viagem;
       public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + viagem.getDataViagem();
    }
}
