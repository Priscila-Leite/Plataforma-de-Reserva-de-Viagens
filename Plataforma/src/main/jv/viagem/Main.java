package main.jv.viagem;

import java.util.ArrayList;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.processos.CSV;
import main.jv.viagem.processos.Funcoes;

public class Main {
    public static void main(String[] args) {
        CSV c = new CSV();
        Funcoes funcoes = new Funcoes();

        // Leitura dos clientes e hotéis dos arquivos CSV
        List<Cliente> clientes = c.leitorClientes("Plataforma/src/main/csv/clientes.csv");
        List<Hotel> hoteis = c.leitorHoteis("Plataforma/src/main/csv/hoteis.csv");
        List<Thread> threadsFuncoes = new ArrayList<>();

        // Cria threads para processar cada cliente com as funções
        for (Cliente cliente : clientes) {
            threadsFuncoes.add(new Thread(funcoes));
        }

        // Inicia as threads para processamento paralelo
        for (Thread thread : threadsFuncoes) {
            thread.start();
        }

        // Aguarda o término de todas as threads antes de continuar
        for (Thread thread : threadsFuncoes) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Seleciona o melhor hotel para cada cliente e define como hotel escolhido
        for (Cliente cliente : clientes) {
            int melhorHotelIndex = funcoes.getMelhorHotelIndex();
            if (melhorHotelIndex >= 0 && melhorHotelIndex < hoteis.size()) {
                cliente.setHotelEscolhido(hoteis.get(melhorHotelIndex));
            } else {
                System.out.println("Não foi possível encontrar um hotel adequado para o cliente: " + cliente.getNome());
            }
        }

        // Exibe informações sobre o hotel escolhido para cada cliente e realiza a escolha do hotel
        for (Cliente cliente : clientes) {
            Hotel hotelEscolhido = cliente.getHotelEscolhido();
            if (hotelEscolhido != null) {
                System.out.println("Hotel escolhido para " + cliente.getNome() + ": " + hotelEscolhido);
                hotelEscolhido.choose();
                System.out.println("Após escolha: " + hotelEscolhido);
            }
        }
    }
}
