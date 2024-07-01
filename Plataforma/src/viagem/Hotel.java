package viagem;

import java.util.LinkedList;

@FunctionalInterface
interface Icmp_hotel {
    boolean cmp(Object ... args);
}

class Hotel implements Runnable{
    private String nome;
    private String localizacao;
    private double vagas;
    private double preco;
    private double estrelas;

    LinkedList<Hotel> lista;
    String nome_arquivo;

    Hotel(String linha[]) {
        this.localizacao = linha[0];
        this.nome = linha[1];
        this.vagas = Double.valueOf(linha[2]);
        this.preco = Double.valueOf(linha[3]);
        this.estrelas = Double.valueOf(linha[4]);
    }

    Hotel(String nome) {
        this.nome_arquivo = nome;
    }

    Hotel() {
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

    public static LinkedList<Hotel> get_registros(String nome){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Hotel> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Hotel reg = new Hotel(linha);            
            lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Hotel> get_registros(String nome, Icmp_hotel f){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Hotel> lista = new LinkedList();
        linha = arquivo.readLine();
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            Hotel reg = new Hotel(linha);
            if(f.cmp(reg)) lista.add(reg);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    public static LinkedList<Hotel> get_registros(String nome, Icmp_hotel f, Object ... args){
        Csv arquivo = new Csv(nome);
        String[] linha;
        LinkedList<Hotel> lista = new LinkedList();
        linha = arquivo.readLine();
        Object argsf[] = new Object[args.length + 1];
        for(int i=0; i<args.length; i++){
            argsf[i+1] = args[i];
        }
        do{
            linha = arquivo.readLine();
            if(linha == null) continue;

            argsf[0] = new Hotel(linha);

            if(f.cmp(argsf)) lista.add((Hotel) argsf[0]);
        } while(linha != null);
        arquivo.close();
        return lista;
    }

    @Override
    public String toString() {
        return String.format("%s-%s-%.0f-%.2f-%.0f", 
            localizacao,
            nome,
            vagas,
            preco,
            estrelas);
    }

    @Override
    public void run(){
        this.lista = get_registros(this.nome_arquivo);
    }
}