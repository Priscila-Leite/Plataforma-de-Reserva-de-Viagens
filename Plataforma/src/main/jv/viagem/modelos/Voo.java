package main.jv.viagem.modelos;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Consumer;

public class Voo{
    private String origem;
    private String destino;
    private String data;
    private int horario;
    private int assentos;
    private int preco;

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

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public Voo(String origem, String destino, String data, int horario, int assentos, int preco){
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horario = horario;
        this.assentos = assentos;
        this.preco = preco;
    }

    public void reservar(){
        this.assentos--;
    }

    public String toString(){
        return String.format("%s,%s,%s,%d,%d,%d", 
            this.origem,
            this.destino,
            this.data,
            this.horario,
            this.assentos,
            this.preco
        );
    }
}