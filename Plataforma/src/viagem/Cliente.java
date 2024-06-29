package viagem;

import java.util.LinkedList;

@FunctionalInterface
interface Icmp_cliente {
    boolean cmp(Cliente v);
}

public class Cliente {
    String saida, chegada;
    int cod, estadia;
    int estrelas;
    float orcamento;

    public Cliente(String linha[]){
        this.cod = Integer.valueOf(linha[0]);
        this.saida = linha[1];
        this.chegada = linha[2];
        this.estadia = Integer.valueOf(linha[3]);
        this.estrelas = Integer.valueOf(linha[4]);
        this.orcamento = Float.valueOf(linha[5]);
    }

    public static LinkedList<Cliente> get_registros(String nome){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Cliente> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Cliente reg = new Cliente(linha);            
            lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Cliente> get_registros(String nome, Icmp_cliente f){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Cliente> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Cliente reg = new Cliente(linha);
            if(f.cmp(reg)) lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    @Override
    public String toString() {
        return String.format("%d-%s-%s-%d-%d-%.2f", 
            this.cod,
            this.saida,
            this.chegada,
            this.estadia,
            this.estrelas,
            this.orcamento);
    }

    public boolean orcamento(int estrelas, int total){
        if ((estrelas >= this.estrelas) && (total <= orcamento)) return true;
        return false;
    }
}
