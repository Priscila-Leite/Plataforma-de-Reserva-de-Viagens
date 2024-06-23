package viagem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {
    String linha, separador = ",";

    public LeitorCSV(){
        
    }

    // 0: cliente, 1: hotel, 2: voo
    public List<Cliente> LeitorClientes(String arquivo){
        List<Cliente> clientes = new ArrayList<>();

        try (BufferedReader l = new BufferedReader(new FileReader(arquivo))) {
            l.readLine();
            
            while ((linha = l.readLine()) != null) {
                String[] dados = linha.split(separador);

                Cliente temp = new Cliente(dados[0], dados[1], dados[2], Integer.parseInt(dados[3]), Integer.parseInt(dados[4]), Float.parseFloat(dados[5]));

                clientes.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("ERRO: Leitura");
        }
        
        return clientes;
    }
}
