package main.jv.viagem;


import java.util.List;
import java.util.Scanner;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.modelos.Voo;
import main.jv.viagem.processos.CSV;
import main.jv.viagem.processos.Funcoes;
import main.jv.viagem.processos.Paralelo;

public class Main {
    public static void main(String[] args){
        Scanner ler = new Scanner(System.in);
        System.out.print("Escolha o fluxo desejado:\n[0] Sequêncial\n[1] Paralelo\n");
        int fluxo = ler.nextInt();
        ler.close();

        CSV csv = new CSV();
        Funcoes funcoes = new Funcoes();

        List<Cliente> clientes = csv.leitorClientes("Plataforma/src/main/csv/clientes_10000.csv");
        List<Hotel> hoteis = csv.leitorHoteis("Plataforma/src/main/csv/hoteis.csv");
        List<Voo> voos = csv.leitorVoos("Plataforma/src/main/csv/voos.csv");

        long startTime, endTime;
        if (fluxo == 0){
            startTime = System.nanoTime();
            String resp = "Plataforma/src/testes/test.csv";
            for (Cliente c : clientes){
                int h = funcoes.melhorHotel(hoteis, c);
                List<Voo> v = funcoes.melhorCaminho(c.getSaida(), c.getChegada(), voos);
                if (h != -1)
                    csv.escreverFinal(c.reservar(hoteis.get(h), v), resp);
                else
                    csv.escreverFinal(c.toString(), resp);
            }
            endTime = System.nanoTime();
        } else {
            startTime = System.nanoTime();
            for (Cliente c : clientes){
                Thread c1 = new Thread(new Paralelo(c, hoteis, voos, csv, funcoes));
                c1.start();
            }
            endTime = System.nanoTime();
        }
        
        long duracao = (endTime - startTime)/1000000000;
        System.out.println("Tempo de execução: " + duracao + " segundos");
    }
}
