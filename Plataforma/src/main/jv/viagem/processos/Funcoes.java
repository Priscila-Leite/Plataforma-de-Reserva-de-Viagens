package main.jv.viagem.processos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;

public class Funcoes {
    public int melhorHotel(List<Hotel> hoteis, int minEstrelas, String local){
        List<Integer> resp = new ArrayList<>();
        int i = 0;
        for(Hotel h : hoteis){
            if ((h.getLocalizacao().equals(local)) && (h.getEstrelas() >= minEstrelas) && (h.getVagas() > 0)){
                resp.add(i);
            }
            i++;
        }

        // Ordena pelo preço
   
        Collections.sort(resp, new Comparator<Integer>(){ 
                public int compare(Integer i1, Integer i2){
                int comparaPreco = Double.compare(hoteis.get(i1).getPreco(), hoteis.get(i2).getPreco());
                
                if (comparaPreco == 0)
                    return Integer.compare(hoteis.get(i1).getEstrelas(), hoteis.get(i2).getEstrelas());
                else
                    return comparaPreco;
                }
            });
        return resp.get(0);
    }

    // reservar hotel

    // Verificar orcamento
    public boolean verOrcamento(Hotel hotel, Voo ida, Voo volta, Cliente cliente){
        double soma = hotel.getPreco() * cliente.getEstadia() + ida.getPreco() + volta.getPreco;
        if (soma <= cliente.getOrcamento()) return true;
        return false;
    }

}
