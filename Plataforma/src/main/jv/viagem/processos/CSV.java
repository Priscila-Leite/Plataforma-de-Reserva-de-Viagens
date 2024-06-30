package main.jv.viagem.processos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;

public class CSV {
    private String linha, separador = ",";
    private List<Hotel> hoteis;
    private List<Cliente> clientes;
    
    // Método para ler clientes a partir de um arquivo CSV
    public List<Cliente> leitorClientes(String arquivo) {
        clientes = new ArrayList<>();

        try (BufferedReader l = new BufferedReader(new FileReader(arquivo))) {
            l.readLine(); // Ignora o cabeçalho
            
            while ((linha = l.readLine()) != null) {
                String[] dados = linha.split(separador);

                // Cria um novo Cliente a partir dos dados do CSV e adiciona à lista
                Cliente temp = new Cliente(dados[0], dados[1], dados[2], Integer.parseInt(dados[3]),
                        Integer.parseInt(dados[4]), Float.parseFloat(dados[5]));

                clientes.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("ERRO: Leitura");
        }
        return clientes;
    }

    // Método para ler hotéis a partir de um arquivo CSV
    public List<Hotel> leitorHoteis(String arquivo) {
        hoteis = new ArrayList<>(); 

        try (BufferedReader l = new BufferedReader(new FileReader(arquivo))) {
            l.readLine(); // Ignora o cabeçalho
            
            while ((linha = l.readLine()) != null) {
                String[] dados = linha.split(separador);

                // Cria um novo Hotel a partir dos dados do CSV e adiciona à lista
                Hotel temp = new Hotel(dados[0], dados[1], Integer.parseInt(dados[2]), Float.parseFloat(dados[3]),
                        Integer.parseInt(dados[4]));

                hoteis.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("ERRO: Leitura");
        }
        return hoteis;
    }

    // Retorna a lista de clientes lidos do arquivo CSV
    public List<Cliente> getlist() {
        return clientes;
    }

    // Retorna a lista de hotéis lidos do arquivo CSV
    public List<Hotel> gethoteis() {
        return hoteis;
    }
}

