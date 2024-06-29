import viagem.Cliente;
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
        // for(Voo e : Voo.get_registros_by_origem("voos.csv", "RIO")){
        //     System.out.println(e);
        // }
        Pesquisador.orcamento();
    }
}
