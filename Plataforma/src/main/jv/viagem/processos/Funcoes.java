package main.jv.viagem.processos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.modelos.Voo;

public class Funcoes extends Thread {
    private CSV a = new CSV();
    private List<Hotel> Hoteis;
    private List<Cliente> clientes;
    private int melhorHotelIndex; // Variável para armazenar o índice do melhor hotel

    public Funcoes() {
        Hoteis = a.gethoteis();
        clientes = a.getlist();
    }

    @Override
    public void run() {
        melhorHotelIndex = melhorHotel();
    }

    public int getMelhorHotelIndex() {
        return melhorHotelIndex;
    }

    private int melhorHotel() {
        List<Integer> resp = new ArrayList<>();
        int i = 0;
        for (Hotel h : Hoteis) {
            for (Cliente C : clientes) {
                if (h.getLocalizacao().equals(C.getChegada()) && h.getEstrelas() >= C.getEstrelas() && h.getVagas() > 0) {
                    resp.add(i);
                }
                i++;
            }
        }
        
        // Ordena pelo preço  
        Collections.sort(resp, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                int comparaPreco = Double.compare(Hoteis.get(i1).getPreco(), Hoteis.get(i2).getPreco());

                if (comparaPreco == 0) {
                    return Integer.compare(Hoteis.get(i1).getEstrelas(), Hoteis.get(i2).getEstrelas());
                } else {
                    return comparaPreco;
                }
            }
        });

        return resp.get(0); // Retorna o índice do melhor hotel
    }

    // Método para verificar se o cliente tem orçamento suficiente
    public boolean verOrcamento(Hotel hotel, Voo ida, Voo volta, Cliente cliente) {
        double soma = hotel.getPreco() * cliente.getEstadia() + ida.getPreco() + volta.getPreco();
        return soma <= cliente.getOrcamento();
    }
}
