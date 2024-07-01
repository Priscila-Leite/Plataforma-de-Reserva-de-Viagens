package main.jv.viagem.modelos;

import java.util.Locale;
import main.jv.viagem.processos.Funcoes;

public class Cliente {
    private String nome, saida, chegada;
    private int estadia;
    private int estrelas;
    private float orcamento;

    public Cliente(String nome,String saida,
                    String chegada,int estadia,
                    int estrelas,float orcamento){
        this.nome = nome;
        this.saida = saida;
        this.chegada = chegada;
        this.estadia = estadia;
        this.estrelas = estrelas;
        this.orcamento = orcamento;
    }

    public String getNome() {
        return nome;
    }
    public String getSaida() {
        return saida;
    }
    public String getChegada() {
        return chegada;
    }
    public double getOrcamento() {
        return orcamento;
    }
    public int getEstrelas() {
        return estrelas;
    }
    public int getEstadia(){
        return estadia;
    }

    @Override
    public String toString() {
        return nome + "," + String.format(Locale.US, "%.2f", orcamento);
    }
    Funcoes f = new Funcoes();
    public double verOrcamento(Hotel hotel/*, List<Voo> voos*/){
        double soma = hotel.getPreco() * estadia;
        //for (Voo v : voos) soma += v.getPreco;

        if (soma <= orcamento) return soma;
        return 0.0;
    }

    public String reservar(Hotel h/*, List<Voo> v*/){
        String dados = this.toString();
        if (verOrcamento(h/*, v*/) > 0) {
            dados += "," + h.toString();
            //for (Voo voo : v) dados += voo.toString();
            dados += "," + String.format(Locale.US, "%.2f", verOrcamento(h));
            h.reservar();
            //v.reservar();
        }
        return dados;
    }
}
