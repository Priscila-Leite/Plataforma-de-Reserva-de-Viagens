import viagem.Cliente;
import viagem.Voos;
import viagem.LeitorCSV;
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
        String pasta = "src/csv/voos.csv";
        for(Voos s : Voos.get_registros_by_destino(pasta, "RIO")){
            System.out.printf("%s\n", s);
        }
    }
}
