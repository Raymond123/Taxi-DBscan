import java.io.*;

public class Main {

    public Main(){
        readCSV("/data/yellow_tripdata_2009-01-15_1hour_clean.csv");
    }

    protected String[][] csv;

    private void readCSV(String f){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            int row=0;

            while ((line = br.readLine()) != null) {
                csv[row] = line.split(",");
                System.out.println(csv[row++]);
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
