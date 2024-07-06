package main.jv.viagem.processos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.modelos.Voo;

public class CSV {
    String linha, separador = ",";

    public List<Cliente> leitorClientes(String arquivo){
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

    public List<Hotel> leitorHoteis(String arquivo){
        List<Hotel> hoteis = new ArrayList<>();

        try (BufferedReader l = new BufferedReader(new FileReader(arquivo))) {
            l.readLine();
            
            while ((linha = l.readLine()) != null) {
                String[] dados = linha.split(separador);

                Hotel temp = new Hotel(dados[0], dados[1], Integer.parseInt(dados[2]), Float.parseFloat(dados[3]), Integer.parseInt(dados[4]));

                hoteis.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("ERRO: Leitura");
        }

        return hoteis;
    }

    public List<Voo> leitorVoos(String arquivo){
        List<Voo> voos = new ArrayList<>();

        try (BufferedReader l = new BufferedReader(new FileReader(arquivo))) {
            l.readLine();
            
            while ((linha = l.readLine()) != null) {
                String[] dados = linha.split(separador);

                String d[] = dados[2].split("/");
                String data = String.format("%s/%s/%s", d[2], d[1], d[0]);

                int horario = Integer.valueOf(dados[3].split(":")[0]) * 60 + Integer.valueOf(dados[3].split(":")[1]);

                Voo temp = new Voo(dados[0], dados[1], data, horario, Integer.valueOf(dados[4]), Integer.valueOf(dados[5]));

                voos.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERRO: Arquivo não encontrado");
        } catch (IOException e) {
            System.out.println("ERRO: Leitura");
        }
        
        return voos;
    }

    public void escreverFinal(String dados, String arquivo) {
        try (BufferedWriter e = new BufferedWriter(new FileWriter(arquivo, true))) {
            e.write(dados);
            e.newLine();
        } catch (IOException x) {
            System.err.println("Erro ao escrever arquivo");
        }
    }
}
