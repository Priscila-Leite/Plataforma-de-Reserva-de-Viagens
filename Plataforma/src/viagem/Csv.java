package viagem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Csv {
    public String path_uri;
    public BufferedReader br;
    public FileReader fr;
    Csv(String path){
        try {
            this.path_uri = new File("").getAbsolutePath() + "/csv/" + path;
            File file = new File(path_uri);
            this.fr = new FileReader(file);
            this.br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] readLine(){
        try {
            String line = this.br.readLine();
            if (line == null) return null;
            return line.split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void close(){
        try {
            if (this.br != null) {
                this.br.close();
            }
            if (this.fr != null) {
                this.fr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
