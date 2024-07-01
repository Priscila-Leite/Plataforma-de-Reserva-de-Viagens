import viagem.Cliente;
import viagem.Cronometro;
import viagem.Voo;
import viagem.LeitorCSV;
import viagem.Pesquisador;
import viagem.Csv;

import java.util.function.Consumer;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner; 

public class Main {
    public static void main(String[] args){
        // priscila(args);
        regasi(args);
    }
    public static void priscila(String[] args){
        // LeitorCSV ler = new LeitorCSV();
        // List<Cliente> clientes = ler.LeitorClientes("Plataforma/src/csv/clientes.csv");
    }
    public static void regasi(String[] args){
        Cronometro t1 = new Cronometro(), t2 = new Cronometro();
        Pesquisador p1 = new Pesquisador(), p2 = new Pesquisador();
        LinkedList<LinkedList<Object>> l1, l2;

        System.out.printf("========== Algoritmo normal ==========\n");

        t1.init();
        l1 = p1.orcamento();
        t1.quit();

        // Pesquisador.imp(l1);
        System.out.println(l1.size());

        System.out.printf("========== Algoritmo com Threads ==========\n");


        t2.init();
        l2 = p2.orcamento_thread(4);
        t2.quit();

        Pesquisador.imp(l2);
        System.out.println(l2.size());

        System.err.printf("Tempo do algoritmo sem threads (em milissegundos): %d\nTempo do algoritmo com threads (em milissegundos): %d\n", t1.get(), t2.get());
    }
}
