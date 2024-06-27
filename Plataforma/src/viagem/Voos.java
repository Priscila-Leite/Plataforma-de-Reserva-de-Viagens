package viagem;

import java.io.*;
import java.util.LinkedList;
import java.util.function.Consumer;

@FunctionalInterface
interface Icmp {
    boolean cmp(Voos v);
}

public class Voos{
    private String origem;
    private String destino;
    private String data;
    private int horario;
    private int assentos;
    private float preco;

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getAssentos() {
        return assentos;
    }

    public void setAssentos(int assentos) {
        this.assentos = assentos;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    Voos(String[] linha){

        this.origem = linha[0];
        this.destino = linha[1];

        // data
        String data[] = linha[2].split("/");
        this.data = String.format("%s/%s/%s", data[2], data[1], data[0]);

        // horario
        String h[] = linha[3].split(":");
        this.horario = Integer.valueOf(h[0]) * 60 + Integer.valueOf(h[1]);

        // assentos
        this.assentos = Integer.valueOf(linha[4]);

        // preço
        this.preco = Integer.valueOf(linha[5]);
    }

    public static LinkedList<Voos> get_registros(String nome){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Voos> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Voos reg = new Voos(linha);            
            lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Voos> get_registros(String nome, Icmp f){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Voos> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Voos reg = new Voos(linha);
            if(f.cmp(reg)) lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Voos> get_registros_by_origem(String nome, String origem){
        Icmp cmp = (Voos v) -> {
            if(v.origem.equals(origem)) return true;
            return false;
        };
        return get_registros(nome, cmp);
    }

    public static LinkedList<Voos> get_registros_by_destino(String nome, String destino){
        Icmp cmp = (Voos v) -> {
            if(v.destino.equals(destino)) return true;
            return false;
        };
        return get_registros(nome, cmp);
    }


    public String toString(){
        return String.format("%s-%s-%s-%d-%d-%.2f", 
            this.origem,
            this.destino,
            this.data,
            this.horario,
            this.assentos,
            this.preco
        );
    }
}