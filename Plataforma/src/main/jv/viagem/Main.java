package main.jv.viagem;

import java.util.ArrayList;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.processos.CSV;
import main.jv.viagem.processos.Funcoes;

public class Main {
    public static void main(String[] args){
        CSV csv = new CSV();
        Funcoes funcoes = new Funcoes();


        List<Cliente> clientes = csv.leitorClientes("Plataforma/src/main/csv/clientes.csv");
        List<Hotel> hoteis = csv.leitorHoteis("Plataforma/src/main/csv/hoteis.csv");

        int temp = funcoes.melhorHotel(hoteis, 4, "SAO");
        System.out.println(hoteis.get(temp));
        hoteis.get(temp).choose();
        System.out.println(hoteis.get(temp));
    }
}
