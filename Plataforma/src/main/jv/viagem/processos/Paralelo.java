package main.jv.viagem.processos;

import java.util.List;

import main.jv.viagem.modelos.Cliente;
import main.jv.viagem.modelos.Hotel;
import main.jv.viagem.modelos.Voo;

public class Paralelo implements Runnable {
    private Cliente cliente;
    private List<Hotel> hoteis;
    private List<Voo> voos;
    private CSV csv;
    private Funcoes funcoes;
    private String saida = "Plataforma/src/testes/test2.csv";

    public Paralelo(Cliente cliente, List<Hotel> hoteis, List<Voo> voos, CSV csv, Funcoes funcoes) {
        this.cliente = cliente;
        this.hoteis = hoteis;
        this.voos = voos;
        this.csv = csv;
        this.funcoes = funcoes;
    }

    @Override
    public void run() {
        int h = funcoes.melhorHotel(hoteis, cliente);
        List<Voo> vl = funcoes.melhorCaminho(cliente.getSaida(), cliente.getChegada(), voos);

        synchronized (csv) {
            if (h != -1) {
                String dadosReserva = cliente.reservar(hoteis.get(h), vl);
                csv.escreverFinal(dadosReserva, saida);
            } else {
                // Se não houver hotel adequado, registrar apenas os dados do cliente
                csv.escreverFinal(cliente.toString(), saida);
            }
        }
    }
}
