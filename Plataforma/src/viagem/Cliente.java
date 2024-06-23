package viagem;

public class Cliente {
    String nome, saida, chegada;
    int estadia;
    int estrelas;
    float orcamento;

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


    @Override
    public String toString() {
        return
                "nome: " + nome +
                ", saida: " + saida +
                ", chegada: " + chegada +
                ", estadia: " + estadia + " dias" +
                ", estrelas: " + estrelas + " estrelas" +
                ", orcamento: R$ " + String.format("%.2f", orcamento) +
                '}';
    }

    public boolean orcamento(int estrelas, int total){
        if ((estrelas >= this.estrelas) && (total <= orcamento)) return true;
        return false;
    }
}
