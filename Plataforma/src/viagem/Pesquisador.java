package viagem;

import java.util.LinkedList;
import java.util.Random;

public class Pesquisador implements Runnable{

    Cliente lista_clientes;
    Hotel lista_hoteis;
    Voo lista_voos;

    Random r;

    int limite_inferior;
    int limite_superior;
    LinkedList<LinkedList<Object>> lista;

    public Pesquisador(){
        this.r = new Random();
    }
    public Pesquisador(Cliente c, Hotel h, Voo v, int i, int m, int n){
        this.r = new Random();

        this.lista_clientes = c;
        this.lista_hoteis = h;
        this.lista_voos = v;

        this.limite_inferior = (n / m) * i;
        this.limite_superior = (n / m) * (i + 1);

        this.lista = new LinkedList<LinkedList<Object>>();
    }
    
    public LinkedList<LinkedList<Object>> orcamento(){

        double total;
        int i;

        // Pegando todas as listas
        this.lista_clientes = new Cliente();
        lista_clientes.lista = Cliente.get_registros("clientes.csv");
        this.lista_hoteis = new Hotel();
        lista_hoteis.lista = Hotel.get_registros("hoteis.csv");
        this.lista_voos = new Voo();
        lista_voos.lista = Voo.get_registros("voos.csv");

        // Criando a lista que vai guardar os caminhos
        LinkedList<LinkedList<Object>> lista = new LinkedList<LinkedList<Object>>();

        // Varre cada cliente
        Hotel h;
        int cont = 0, j;
        for(Cliente c : lista_clientes.lista){
            LinkedList<Object> l = new LinkedList<Object>();

            // Varre os voos que saem do mesmo lugar
            for(Voo v1 : lista_voos.lista){
                if(v1.getOrigem().equals(c.saida)){

                    // Se for um voo direto
                    if(v1.getDestino().equals(c.chegada)){
                        h = get_valid_hotel(v1, c);
                        if(h == null) continue;

                        l.add(v1);
                        l.add(h);
                        lista.add(l);
                        break;

                    // Se for uma possível rota dupla
                    } else{
                        for(Voo v2 : lista_voos.lista){
                            if(v2.getDestino().equals(c.chegada)){
                                h = get_valid_hotel(v1, v2, c);
                                if(h == null) continue;

                                l.add(v1);
                                l.add(v2);
                                l.add(h);
                                lista.add(l);
                                break;
                            }    
                        }
                        if(l.size() == 3) break;
                    }
                }
            }

            
        }

        return lista;
    }

    public Hotel get_valid_hotel(Voo v1, Cliente c){
        double preco;
        for(Hotel h : this.lista_hoteis.lista){
            if(h.getLocalizacao().equals(c.chegada)){
                preco = v1.getPreco() + h.getPreco(); 
                if((preco <= c.orcamento) && (h.getVagas() > 0) && (this.r.nextInt(2) != 0)){
                    h.setVagas(h.getVagas()-1);
                    return h;
                }
            }
        }
        return null;
    }
    public Hotel get_valid_hotel(Voo v1, Voo v2, Cliente c){
        double preco;
        for(Hotel h : this.lista_hoteis.lista){
            if(h.getLocalizacao().equals(c.chegada)){
                preco = v1.getPreco() + v2.getPreco() + h.getPreco(); 
                if((preco <= c.orcamento) && (h.getVagas() > 0) && (this.r.nextInt(2) != 0)){
                    h.setVagas(h.getVagas()-1);
                    return h;
                }
            }
        }
        return null;
    }

    public static void imp(LinkedList<LinkedList<Object>> l){
        Voo v1, v2;
        Hotel h;
        for(LinkedList<Object> sl : l){
            if(sl.size() == 2){
                v1 = (Voo) sl.get(0);
                h = (Hotel) sl.get(1);
                System.out.printf("%s\n%s\n\n", v1, h);
            } else{
                v1 = (Voo) sl.get(0);
                v2 = (Voo) sl.get(1);
                h = (Hotel) sl.get(2);
                System.out.printf("%s\n%s\n%s\n\n", v1, v2, h);
            }
        }
    }

    public LinkedList<LinkedList<Object>> orcamento_thread(int n){
        this.lista_clientes = new Cliente("clientes.csv");
        this.lista_voos = new Voo("voos.csv");
        this.lista_hoteis = new Hotel("hoteis.csv");

        // Rodando as Threads
        Thread  t1 = new Thread(this.lista_clientes),
                t2 = new Thread(this.lista_voos),
                t3 = new Thread(this.lista_hoteis);
        t1.start();
        t2.start();
        t3.start();

        // Unindo as threads
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace(); 
            Thread.currentThread().interrupt();
            return new LinkedList<>();
        }

        // Configurando as threads
        Pesquisador p[] = new Pesquisador[n];
        Thread t[] = new Thread[n];
        for(int i=0; i<n; i++){
            p[i] = new Pesquisador(this.lista_clientes, this.lista_hoteis, this.lista_voos, i, n, this.lista_clientes.lista.size());
            t[i] = new Thread(p[i]);

            // Rodando as Threads
            t[i].start();
        }

        // Unindo as threads
        try {
            for (int i = 0; i < n; i++) {
                t[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return new LinkedList<>();
        }

        // Montando o resultado
        LinkedList<LinkedList<Object>> lista = new LinkedList<LinkedList<Object>>();
        for(Pesquisador pp : p){
            for(LinkedList<Object> e : pp.lista){
                lista.add(e);
            }
        }

        return lista;
    }

    public void run(){

        // Varre cada cliente
        Hotel h;
        for(int i=this.limite_inferior; i<limite_superior; i++){
            Cliente c = this.lista_clientes.lista.get(i);
            LinkedList<Object> l = new LinkedList<Object>();

            // Varre os voos que saem do mesmo lugar
            for(Voo v1 : lista_voos.lista){
                if(v1.getOrigem().equals(c.saida)){

                    // Se for um voo direto
                    if(v1.getDestino().equals(c.chegada)){
                        h = get_valid_hotel(v1, c);
                        if(h == null) continue;

                        l.add(v1);
                        l.add(h);
                        lista.add(l);
                        break;

                    // Se for uma possível rota dupla
                    } else{
                        for(Voo v2 : lista_voos.lista){
                            if(v2.getDestino().equals(c.chegada)){
                                h = get_valid_hotel(v1, v2, c);
                                if(h == null) continue;

                                l.add(v1);
                                l.add(v2);
                                l.add(h);
                                lista.add(l);
                                break;
                            }    
                        }
                        if(l.size() == 3) break;
                    }
                }
            }
        }
    }
}


// Motivação/Problema: usuários entram na plataforma e solicitam um orçamento de viagem; depois eles avaliam e aceitam ou não esse orçamento (considerando valor e requisitos de qualidade dos hoteis). O orçamento envolve escolher o hotel mais barato disponível para a quantidade de estrelas pedidas pelo usuário (exemplo, se pediu no mínimo 3 estrelas, pode ser 3, 4 ou 5, mas não 1 ou 2). O orçamento também envolve pegar o ponto de saída do usuário e encontrar vôos mais baratos até chegar no destino. Exemplo: usuário mora no RIO e quer chegar em Palmas/TO (PMW), possivelmente ele precisará encontrar um vôo RIO x Belo Horizonte/MG (CNF) e outro CNF x PMW. Para simplificar, iremos considerar no máximo duas conexões. À medida que usuários aceitam ou não o orçamento, os hoteis e vôos podem ir se esgotando ou não, impactando assim os próximos usuários.

// Implementações
// Existirão dois fluxos básicos, um mais lento (serial/sequencial) e outro mais rápido (paralelo/tempo real):

// uma lista de usuários deverá, sequencialmente (sem threads), ser processada, sendo que a escolha do usuário aceitar ou não o orçamento (de acordo com valor disponível)
// a mesma lista de usuários deve ser enviada ao sistema para reservas simultâneas (por exemplo, lançar blocos de B=10 usuários), então será importante verificar que não existam inconsistências, à medida que novos pedidos chegam e aceitam ou não, se isso não modifica o orçamento dado previamente ao usuário (no caso, é necessário enviar outro orçamento para que ele avalie novamente o novo preço).
// Façam experimentos computacionais e meçam quanto tempo leva para executar um conjunto de:

// 100 clientes
// 1000 clientes
// 10000 clientes
// Experimente até que o tempo de execução Sequencial seja de minutos. O algoritmo com threads deverá ser, naturalmente, mais rápido do que o sequencial, mas caso não seja, explique as razões.

// IMPORTANTE: os grupos deverão especificar as classes e gerar os próprios arquivos para testes, explorando características de interesse, dentro das especificações do trabalho (ou seja, se o código está mais rápido no computador utilizado, pode ser necessário gerar arquivos maiores).

