import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.round;

public class Main {

    protected HashMap<TripRecord, Boolean> tripRecords; // array list of trip record, for clustering
    protected int eps;
    protected int minPts;
    protected final float eRM = 6371;//*1000;

    public Main(){
        //readCSV("data/yellow_tripdata_2009-01-15_1hour_clean.csv");
        //inputs();
        //dbScan(this.tripRecords, 5, 10);
        // 1.99 - -73.947828,40.787172 - -73.952943,40.76771

        float dist = euclidDist(new GPScoord((float)-73.947828, (float)40.787172),
                                new GPScoord((float)-73.952943, (float)40.76771));
        System.out.println(dist);
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
     * computes the distance between 2 given gps coordinates using the haversine formula for calculating distances between GPS coordinates.
     * Formula can be found here: http://www.movable-type.co.uk/scripts/latlong.html
     * And code was referenced from here: https://stackoverflow.com/questions/365826/calculate-distance-between-2-gps-coordinates
     * @param p1
     * first point, type GPScoord
     * @param p2
     * second point, type GPScoord
     * @return
     * flaot value that is the calculated distance between the 2 given points in meters. Can change to KM by removing the (*1000) in the eRM variable.
     */
    private float euclidDist(GPScoord p1, GPScoord p2){
        float dlat = degToRad(p1.getLat() - p2.getLat());
        float dlon = degToRad(p1.getLon() - p2.getLon());

        float p1Lat = degToRad(p1.getLat());
        float p2Lat = degToRad(p2.getLat());

        float a = (float) ((Math.sin(dlat/2)*Math.sin(dlat/2)) +
                (Math.sin(dlon/2)*Math.sin(dlon/2)) * Math.cos(p1Lat) * Math.cos(p2Lat));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
        //System.out.println((float) Math.sqrt((x*x)+(y*y)));
        return (float) (eRM * c);
    }

    private float degToRad(float d){
        return ((float)(d*Math.PI/180));
    }

    private String[] dbScan(HashMap<TripRecord, Boolean> db, int eps, int minPts){
        int c = 0;
        for (TripRecord k : db.keySet()){
            if((db.get(k))){ continue; }

            db.put(k, true);
            HashMap<TripRecord, Boolean> neighborPts = regionQry(k, eps, db.keySet());
            if (neighborPts.size() < minPts) {

            }else{
                expandCluster(k.pickup_Location, neighborPts,
                        new Cluster(c, new ArrayList<>()), eps, minPts);
                c++;
            }
        }

        return null;
    }

    private void expandCluster(GPScoord p, HashMap<TripRecord, Boolean> neighbours,
                               Cluster c, int eps, int minPts) {
        c.addGPS(p);
        for(TripRecord g : neighbours.keySet()){
            if (!(neighbours.get(g))){
                neighbours.put(g, true);
                HashMap<TripRecord, Boolean> neighbours_p = regionQry(g, eps, neighbours.keySet());
                if(neighbours_p.size() >= minPts){
                    neighbours.putAll(neighbours_p);
                }
            }

            // if(), p not in a cluster, add it to c
        }
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
    private HashMap<TripRecord, Boolean> regionQry(TripRecord p, int eps, Set<TripRecord> global){
        HashMap<TripRecord, Boolean> neighbours = new HashMap<>();
        neighbours.put(p, true);
        for(TripRecord n : global){
            if( euclidDist(p.getPickup_Location(), n.getPickup_Location()) < eps){
                neighbours.put(n, false);
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
            br.readLine(); // skips first line with attributes
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
