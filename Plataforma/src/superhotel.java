import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Gerenciamento {
    void carregarCsv(String arquivoCsv);
    List<Hotel> getLista();
}

class Hoteis implements Gerenciamento {
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
                String nome = dados[1];
                double vagas = extrairNumero(dados[2]);
                double preco = extrairNumero(dados[3]);
                double estrelas = extrairNumero(dados[4]);
                listaHoteis.add(new Hotel(localizacao, nome, vagas, preco, estrelas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double extrairNumero(String valor) {
        String numeroTexto = valor.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numeroTexto);
    }

    @Override
    public List<Hotel> getLista() {
        return listaHoteis;
    }
}

class Cliente extends Thread {
    private String nome;
    private String preferenciaLocalizacao;
    private double dias;
    private double preferenciaPreco;
    private double estrelas;
    private Hoteis hoteis;
    private Hotel hotelEscolhido;

    public Cliente(String nome, String preferenciaLocalizacao, double dias, double preferenciaPreco, double estrelas, Hoteis hoteis) {
        this.nome = nome;
        this.preferenciaLocalizacao = preferenciaLocalizacao;
        this.dias = dias;
        this.preferenciaPreco = preferenciaPreco;
        this.estrelas = estrelas;
        this.hoteis = hoteis;
    }

    public void run() {
        escolherHotel();
        Main.imprimirEscolha(this); 
    }

    private void escolherHotel() {
        List<Hotel> listaHoteis = hoteis.getLista();
        if (!listaHoteis.isEmpty()) {
            listaHoteis = listaHoteis.stream()
                    .filter(h -> h.getLocalizacao().equals(preferenciaLocalizacao) && h.getPreco() <= preferenciaPreco && h.getEstrelas() >= estrelas)
                    .collect(Collectors.toList());
            if (!listaHoteis.isEmpty()) {
                int index = (int) (Math.random() * listaHoteis.size());
                hotelEscolhido = listaHoteis.get(index);
            }
        }
    }

    public Hotel getHotelEscolhido() {
        return hotelEscolhido;
    }

    public String getNome() {
        return nome;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public double getPreco() {
        return preco;
    }

    public double getEstrelas() {
        return estrelas;
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
                String[] dados = linha.split(";");
                if (dados.length == 6) { // Verificando o número correto de campos
                    String nome = dados[0];
                    String preferenciaLocalizacao = dados[1];
                    double dias = extrairNumero(dados[3]); // Ignorando "dias"
                    double preferenciaPreco = extrairNumero(dados[5]); // Pegando o preço após a formatação
                    double estrelas = extrairNumero(dados[4]); // Pegando estrelas
                    listaClientes.add(new Cliente(nome, preferenciaLocalizacao, dias, preferenciaPreco, estrelas, hoteis));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double extrairNumero(String valor) {
        String numeroTexto = valor.replaceAll("[^\\d.]", "");
        return Double.parseDouble(numeroTexto);
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}

public class superhotel {
    private static final Object lock = new Object();

    public static void imprimirEscolha(Cliente cliente) {
        synchronized (lock) {
            Hotel hotelEscolhido = cliente.getHotelEscolhido();
            if (hotelEscolhido != null) {
                System.out.println(cliente.getNome() + " escolheu o hotel: " + hotelEscolhido.getNome());
            } else {
                System.out.println(cliente.getNome() + " não encontrou um hotel que atenda às suas preferências.");
            }
        }
    }

    public static void main(String[] args) {
        String diretorioHoteis = "c:\\Users\\lucas\\Downloads\\formato-hoteis.csv";
        String arquivoClientes = "c:\\Users\\lucas\\Downloads\\formato-clientes.csv";

        Hoteis hoteis = new Hoteis(diretorioHoteis);
        Clientes clientes = new Clientes(arquivoClientes, hoteis);

        List<Thread> threadsClientes = new ArrayList<>();
        for (Cliente cliente : clientes.getListaClientes()) {
            threadsClientes.add(new Thread(cliente));
        }

        for (Thread cliente : threadsClientes) {
            cliente.start();
        }

        for (Thread cliente : threadsClientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Criando arquivo de saída CSV
        criarArquivoSaida(clientes);

        System.out.println("Todos os clientes escolheram seus hotéis.");
    }

    private static void criarArquivoSaida(Clientes clientes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("saida.csv"))) {
            writer.println("Nome Cliente; Hotel Escolhido");

            for (Cliente cliente : clientes.getListaClientes()) {
                Hotel hotelEscolhido = cliente.getHotelEscolhido();
                if (hotelEscolhido != null) {
                    writer.println(cliente.getNome() + ";" + hotelEscolhido.getNome());
                } else {
                    writer.println(cliente.getNome() + ";Nenhum hotel encontrado");
                }
            }

            System.out.println("Arquivo de saída criado com sucesso: saida.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
