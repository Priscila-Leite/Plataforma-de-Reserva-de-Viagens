package main.jv.viagem.modelos;

import java.util.Locale;

public class Hotel {
    private String nome, localizacao;
    private double preco;
    private int vagas, estrelas;

    public Hotel(String localizacao, String nome, int vagas, double preco, int estrelas){
        this.localizacao = localizacao;
        this.nome = nome;
        this.vagas = vagas;
        this.preco = preco;
        this.estrelas = estrelas;
    }

    public String getNome() {
        return nome;
    }
    public String getLocalizacao() {
        return localizacao;
    }
    public double getPreco() {
        return preco;
    }
    public int getEstrelas() {
        return estrelas;
    }
    public int getVagas() {
        return vagas;
    }
    public void reservar(){
        if (vagas > 0)
            this.vagas--;
    }
    

    @Override
    public String toString() {
        return nome + "," + estrelas + "," + String.format(Locale.US, "%.2f", preco);
    }
}
