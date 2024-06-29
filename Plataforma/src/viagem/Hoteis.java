import java.io.*;
import java.nio.file.*;
import java.util.*;

interface Gerenciamento {
    void carregarCsv(String arquivoCsv);
    List<Hotel> getLista();
}

public class Hoteis implements Gerenciamento {
    private List<Hotel> listaHoteis;

    public Hoteis(String arquivoCsv) {
        listaHoteis = new ArrayList<>();
        carregarCsv(arquivoCsv);
    }

    @Override
    public void carregarCsv(String arquivoCsv) {
        Path path = Paths.get(arquivoCsv);
        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] dados = linha.split(";");
                String localizacao = dados[0];
                String hotel = dados[1];
                double vagas = Double.parseDouble(dados[2]);
                double preco = Double.parseDouble(dados[3]);
                double estrelas = Double.parseDouble(dados[4]);
                listaHoteis.add(new Hotel(localizacao, hotel, vagas, preco, estrelas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Hotel> getLista() {
        return listaHoteis;
    }
}

class Cliente extends Thread {
    private String nome;
    private String preferenciaLocalizacao;
    private double preferenciaPreco;
    private double estrelas;
    private Hoteis hoteis;

    public Cliente(String nome, String preferenciaLocalizacao, double preferenciaPreco, double estrelas, Hoteis hoteis) {
        this.nome = nome;
        this.preferenciaLocalizacao = preferenciaLocalizacao;
        this.preferenciaPreco = preferenciaPreco;
        this.estrelas = estrelas;
        this.hoteis = hoteis;
    }

    public void run() {
        escolherHotel();
    }

    private void escolherHotel() {
        List<Hotel> listaHoteis = hoteis.getLista();
        if (!listaHoteis.isEmpty()) {
            listaHoteis = listaHoteis.stream()
                    .filter(h -> h.getLocalizacao().equals(preferenciaLocalizacao) && h.getPreco() <= preferenciaPreco && h.getEstrelas() >= estrelas)
                    .toList();
            if (!listaHoteis.isEmpty()) {
                int index = (int) (Math.random() * listaHoteis.size());
                Hotel hotelEscolhido = listaHoteis.get(index);
                System.out.println(nome + " escolheu o hotel: " + hotelEscolhido);
            } else {
                System.out.println(nome + " não encontrou hotéis disponíveis que atendam às suas preferências.");
            }
        } else {
            System.out.println(nome + " não encontrou hotéis disponíveis.");
        }
    }
}

class Hotel {
    private String nome;
    private String localizacao;
    private double vagas;
    private double preco;
    private double estrelas;

    public Hotel(String localizacao, String nome, double vagas, double preco, double estrelas) {
        this.localizacao = localizacao;
        this.nome = nome;
        this.vagas = vagas;
        this.preco = preco;
        this.estrelas = estrelas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public double getVagas() {
        return vagas;
    }

    public void setVagas(double vagas) {
        this.vagas = vagas;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(double estrelas) {
        this.estrelas = estrelas;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nome='" + nome + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", vagas=" + vagas +
                ", preco=" + preco +
                ", estrelas=" + estrelas +
                '}';
    }
}

class Clientes {
    private List<Cliente> listaClientes;

    public Clientes(String arquivoClientes, Hoteis hoteis) {
        listaClientes = new ArrayList<>();
        carregarClientes(arquivoClientes, hoteis);
    }

    private void carregarClientes(String arquivoClientes, Hoteis hoteis) {
        Path path = Paths.get(arquivoClientes);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    String nome = dados[0];
                    String preferenciaLocalizacao = dados[1];
                    double preferenciaPreco = Double.parseDouble(dados[2]);
                    double estrelas = Double.parseDouble(dados[3]);
                    listaClientes.add(new Cliente(nome, preferenciaLocalizacao, preferenciaPreco, estrelas, hoteis));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}

// public class Main {
//     public static void main(String[] args) {
//         String diretorioHoteis = "caminho/para/arquivo/hoteis.csv"; 
//         String arquivoClientes = "caminho/para/arquivo/clientes.csv"; 

//         Hoteis hoteis = new Hoteis(diretorioHoteis);
//         Clientes clientes = new Clientes(arquivoClientes, hoteis);

//         List<Thread> threadsClientes = new ArrayList<>();
//         for (Cliente cliente : clientes.getListaClientes()) {
//             threadsClientes.add(cliente);
//             cliente.start();
//         }

//         for (Thread cliente : threadsClientes) {
//             try {
//                 cliente.join();
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//         }

//         System.out.println("Todos os clientes escolheram seus hotéis.");
//     }
// }
