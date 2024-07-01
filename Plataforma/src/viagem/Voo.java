package viagem;

import java.io.*;
import java.util.LinkedList;
import java.util.function.Consumer;

@FunctionalInterface
interface Icmp_voo {
    boolean cmp(Object ... args);
}

public class Voo implements Runnable{
    private String origem;
    private String destino;
    private String data;
    private int horario;
    private int assentos;
    private Double preco;

    LinkedList<Voo> lista;
    String nome_arquivo;

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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    Voo(String[] linha){

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
        this.preco = Double.valueOf(linha[5]);
    }

    Voo(String nome){
        this.nome_arquivo = nome;
    }

    Voo(){
    }

    public static LinkedList<Voo> get_registros(String nome){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Voo> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Voo reg = new Voo(linha);            
            lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Voo> get_registros(String nome, Icmp_voo f){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Voo> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Voo reg = new Voo(linha);
            if(f.cmp(reg)) lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Voo> get_registros(String nome, Icmp_voo f, Object ... args){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Voo> lista = new LinkedList();
        linha = arquivo.readLine();
        Object argsf[] = new Object[args.length + 1];
        for(int i=0; i<args.length; i++){
            argsf[i+1] = args[i];
        }
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            argsf[0] = new Voo(linha);

            if(f.cmp(argsf)) lista.add((Voo) argsf[0]);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<LinkedList<Voo>> get_caminho_duplo(String nome, String origem, String destino){

        // Abre o arquivo
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<LinkedList<Voo>> lista_final = new LinkedList<LinkedList<Voo>>();
        LinkedList<Voo> lista;
        linha = arquivo.readLine();

        // Prepara a o parâmetro e as funções auxiliáres para get
        Icmp_voo cmp = (Object ... args) -> {
            Voo v = (Voo) args[0];
            String o = (String) args[1], d = (String) args[2];
            if(v.origem.equals(o) && v.destino.equals(d)) return true;
            return false;
        };
        Object args1[] = new Object[3], args2[] = new Object[3];
        args1[1] = origem;
        args1[2] = destino;
        args2[2] = destino;

        // Pega todos os registros
        LinkedList<Voo> lista_registros = get_registros(nome);

        // Varre os registros e verifica se é um caminho direto e caminho duplo
        for(Voo v1 : lista_registros){
            lista = new LinkedList<Voo>();
            args1[0] = v1;
            if(cmp.cmp(args1)) lista.add(v1);
            else{

                args2[1] = v1.destino;
                for(Voo v2 : lista_registros){
                    args2[0] = v2;
                    if(cmp.cmp(args2)) lista.add(v2);
                }
            }
            if(lista.size() > 0) lista_final.add(lista);
        }

        arquivo.close();
        return lista_final;
    }

    public static LinkedList<Voo> alter_list_voo_by_destino(String nome, LinkedList<Voo> lista, String destino){

        for(int i=0; i<lista.size(); i++)
            if(!lista.get(i).destino.equals(destino))
                lista.remove(i);
        return lista;
    }

    @Override
    public void run(){
        this.lista = get_registros(this.nome_arquivo);
    }

    public String toString(){
        return String.format("%s-%s-%s-%d-%d-%.0f", 
            this.origem,
            this.destino,
            this.data,
            this.horario,
            this.assentos,
            this.preco
        );
    }
}