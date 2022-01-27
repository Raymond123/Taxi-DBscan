import java.awt.geom.FlatteningPathIterator;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;

public class Main {

    protected ArrayList<TripRecord> tripRecords; // array list of trip record, for clustering

    public Main(){
        readCSV("data/yellow_tripdata_2009-01-15_1hour_clean.csv");
    }

    /**
     * Method reads a csv file into an array list,
     * each element in the array list is a row from the csv file
     * each element in the arrays in the list are the columns from the csv file
     *
     * @param f
     * File path for the csv file that is to be read
     */
    private void readCSV(String f){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            this.tripRecords = new ArrayList<>();
            String[] attr = br.readLine().split(",");

            System.out.println(Arrays.toString(attr));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                tripRecords.add(
                        new TripRecord(
                                lineArr[4], // index 4 is Trip_Pickup_DateTime
                                new GPScoord(Float.parseFloat(lineArr[8]), Float.parseFloat(lineArr[9])), // index 8/9 is start lon/lat
                                new GPScoord(Float.parseFloat(lineArr[12]), Float.parseFloat(lineArr[13])), // index 12/13 is end lon/lat
                                Float.parseFloat(lineArr[7])
                        )
                );
            }

            System.out.println(tripRecords.get(0).getTrip_Distance());
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
