import viagem.Cliente;
import viagem.LeitorCSV;
import java.util.List;

public class Main {
    public static void main(String[] args){
        LeitorCSV ler = new LeitorCSV();
        List<Cliente> clientes = ler.LeitorClientes("Plataforma/src/csv/clientes.csv");
    }
}
