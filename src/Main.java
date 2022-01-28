import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

import static java.lang.Math.round;

public class Main {

    protected HashMap<TripRecord, Boolean> tripRecords; // array list of trip record, for clustering
    protected int eps;
    protected int minPts;

    public Main(){
        readCSV("data/yellow_tripdata_2009-01-15_1hour_clean.csv");
        //inputs();
        dbScan(tripRecords, 5, 10);
    }

    /**
     * method just for taking inputs and assigning those inputted values to eps and minPts.
     */
    private void inputs(){
        Scanner scan = new Scanner(System.in);

        this.minPts = Integer.parseInt(scan.nextLine());
        this.eps = Integer.parseInt(scan.nextLine());
    }

    /**
     * computes the euclidean distance between 2 gps coordinate points
     * @param p1
     * first point, type GPScoord
     * @param p2
     * second point, type GPScoord
     * @return
     * flaot value that is the calculated distance between the 2 given points
     */
    private float euclidDist(GPScoord p1, GPScoord p2){
        float x = p1.getLat() - p2.getLat();
        float y = p1.getLon() - p2.getLon();

        System.out.println((float) Math.sqrt((x*x)+(y*y)));
        return (float) Math.sqrt((x*x)+(y*y));
    }

    private String[] dbScan(HashMap<TripRecord, Boolean> db, int eps, int minPts){
        int c = 0;
        for (TripRecord k : db.keySet()){
            if((db.get(k))){ continue; }

            db.put(k, true);
            ArrayList<GPScoord> neighborPts = regionQry(k.pickup_Location, eps, db.keySet());
            if (neighborPts.size() < minPts) {

            }else{
                expandCluster(k.pickup_Location, neighborPts, c, eps, minPts);
                c++;
            }
        }

        return null;
    }

    private void expandCluster(GPScoord p, ArrayList<GPScoord> neighbours,
                               int c, int eps, int minPts) {

    }

    /**
     * Method to find all the points around a central point, p, that are within
     * a given distance, eps, and returns them as a set/array
     *
     * @param p
     * A point to be at the center of the region
     * @param eps
     * Maximum distance that can be between p and another point for that point to be in the region
     * @return
     * return the set of all points that are in the given eps of the point p
     */
    private ArrayList<GPScoord> regionQry(GPScoord p, int eps, Set<TripRecord> global){
        ArrayList<GPScoord> neighbours = new ArrayList<>();
        neighbours.add(p);
        for(TripRecord n : global){
            if( euclidDist(p, n.getPickup_Location()) < eps){
                neighbours.add(n.getPickup_Location());
            }
        }

        return neighbours;
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
            this.tripRecords = new HashMap<>();
            //String[] attr = br.readLine().split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                tripRecords.put(
                        new TripRecord(
                                lineArr[4], // index 4 is Trip_Pickup_DateTime
                                new GPScoord(Float.parseFloat(lineArr[8]), Float.parseFloat(lineArr[9])), // index 8/9 is start lon/lat
                                new GPScoord(Float.parseFloat(lineArr[12]), Float.parseFloat(lineArr[13])), // index 12/13 is end lon/lat
                                Float.parseFloat(lineArr[7])
                        ),
                        false
                );
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
