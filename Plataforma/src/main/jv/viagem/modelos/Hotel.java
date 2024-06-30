package main.jv.viagem.modelos;

public class Hotel {
    private String nome, localizacao;
    private double preco;
    private int vagas, estrelas;
    private int vagasAtual;

    public Hotel(String localizacao, String nome, int vagas, double preco, int estrelas){
        this.localizacao = localizacao;
        this.nome = nome;
        this.vagas = vagas;
        this.preco = preco;
        this.estrelas = estrelas;
        vagasAtual = vagas;
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
    public void choose(){
        vagasAtual -= 1;
    }
    

    @Override
    public String toString() {
        return
                "nome: " + nome +
                ", localizacao: " + localizacao +
                ", estrelas: " + estrelas + " estrelas" +
                ", preco: R$ " + String.format("%.2f", preco) +
                ", vagas: " + vagas;
    }
}
