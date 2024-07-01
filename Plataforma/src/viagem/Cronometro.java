package viagem;

public class Cronometro {
    long ti, tf;
    public void init(){
        this.ti = System.currentTimeMillis();
    }
    public void quit(){
        this.tf = System.currentTimeMillis();
    }
    public long get(){ 
        return this.tf - this.ti; 
    }
}
