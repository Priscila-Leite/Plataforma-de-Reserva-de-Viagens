package main.jv.viagem.processos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.modelos.Voo;

public class Funcoes {
    public int melhorHotel(List<Hotel> hoteis, Cliente c){
        List<Integer> resp = new ArrayList<>();
        int i = 0;
        for(Hotel h : hoteis){
            if ((h.getLocalizacao().equals(c.getChegada())) && (h.getEstrelas() >= c.getEstrelas()) && (h.getVagas() > 0))
                resp.add(i);
            
            i++;
        }

        if (resp.size() == 0) return -1;

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

    public List<Voo> melhorCaminho(String saida, String chegada, List<Voo> voos){
        List<Voo> melhor =  new LinkedList<Voo>();
        int soma_melhor = 0;
        int soma;
        for(Voo v : voos){
            if(saida.equals(v.getOrigem())){
                if(chegada.equals(v.getDestino())){
                    if((v.getPreco() < soma_melhor) || (melhor.size() == 0)) {
                        melhor.clear();
                        melhor.add(v);
                        soma_melhor = v.getPreco();
                    }
                } else{
                    for(Voo v2 : voos){
                        soma = v.getPreco();
                        if(v2.getOrigem().equals(v.getDestino()) && v2.getDestino().equals(chegada)){
                            soma += v2.getPreco();
                            if((soma < soma_melhor) || (melhor.size() == 0)){
                                soma_melhor = soma;
                                melhor.clear();
                                melhor.add(v);
                                melhor.add(v2);
                            }
                        }
                    }
                }
            }
        }
        return melhor;
    }
}
