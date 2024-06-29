package viagem;

import java.util.LinkedList;

public class Pesquisador {
    
    public static int orcamento(){

        LinkedList<Cliente> lista_clientes = Cliente.get_registros("clientes.csv");

        // Varre todos os clientes
        for(Cliente c : lista_clientes){

            
            LinkedList<Voo> lista_voos = Voo.get_caminho("voos.csv", c.saida, c.chegada);
            // for(Voo v : lista_voos) System.out.printf("%s\n", v);

        }
        


        //     Dado dois pontos, para cada caminho possível (de no máximo 3 pontos):
        //         Verifica se o usuário vai aceitar o orçamento
        //         Se sim quebra o laço
        //         Se não vai para o próximo
                
        return 1;
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

