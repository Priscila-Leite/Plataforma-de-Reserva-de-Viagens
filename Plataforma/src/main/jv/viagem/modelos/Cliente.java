package main.jv.viagem.modelos;

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
        return
                "nome: " + nome +
                ", saida: " + saida +
                ", chegada: " + chegada +
                ", estadia: " + estadia + " dias" +
                ", estrelas: " + estrelas + " estrelas" +
                ", orcamento: R$ " + String.format("%.2f", orcamento);
    }
  
}