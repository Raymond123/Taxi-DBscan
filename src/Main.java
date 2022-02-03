import java.io.*;
import java.util.*;
import java.util.List;
@SuppressWarnings("SpellCheckingInspection")
public class Main {

    protected List<TripRecord> tripRecords; // array list of trip record, for clustering
    protected List<Cluster> clusters; // array list to store all clusters in
    protected float eps;
    protected int minPts;

    public Main(){
        this.clusters = new ArrayList<>();

        readCSV("data/yellow_tripdata_2009-01-15_1hour_clean.csv");
        //inputs();
        //dbScan(this.tripRecords, 0.0001f, 5);
        dbScan2(this.tripRecords, 0.0001f, 5);
        for (Cluster c : this.clusters){
            c.printClust();
        }
    }

    /**
     * method just for taking inputs and assigning those inputted values to eps and minPts.
     */
    private void inputs(){
        Scanner scan = new Scanner(System.in);
        this.minPts = scan.nextInt();
        this.eps = scan.nextFloat();
    }

    private float euclidDist(GPScoord p1, GPScoord p2){
        float x = (p2.getLat()-p1.getLat());
        float y = (p2.getLon()-p1.getLon());

        float dist = (x*x) + (y*y);
        return (float) Math.sqrt(dist);
    }

    /**
     * Implementation of DBSCAN algorithm using ArrayList as data type for db.
     * -> https://cse.buffalo.edu/~jing/cse601/fa13/materials/clustering_density.pdf
     * above is a powerpoint that includes the pseudo-code the below algorithm is based off
     * @param db
     * A HashMap, with the key being the TripRecord and each value being that
     * TripRecords/GPSCo-ord's visited value
     * @param eps
     * The maximum distance that can be between points in order for them to still be
     * considered in the same cluster.
     * @param minPts
     * The minimum number of points that need to be grouped up in order for the algorithm
     * to consider that group a cluster.
     */
    private void dbScan(List<TripRecord> db, float eps, int minPts){
        int c = 0;
        for (TripRecord tr : db){
            if(tr.getVisited()) { continue; }

            tr.visit();
            List<TripRecord> neighbours = regionQry(tr, eps);
            if(neighbours.size() < minPts) {
                tr.noise();
                continue;
            }
            Cluster clstr =  new Cluster(++c);
            expandCluster( tr, neighbours, clstr, eps, minPts );
            clstr.setCenter();
            this.clusters.add(clstr);
        }
    }


    /**
     * Implementation of DBSCAN algorithm using ArrayList as data type for db.
     * This version of the DBSCAN algorithm is based off the psuedo code provided in the assignment outline
     * Main difference is the lack of expandCluster() method and the use of a label[] array
     * @param db
     * An ArrayList of TripRecords that was created from the data in the inputted csv file
     * @param eps
     * The maximum distance that can be between points in order for them to still be
     * considered in the same cluster.
     * @param minPts
     * The minimum number of points that need to be grouped up in order for the algorithm
     * to consider that group a cluster.
     */
    private void dbScan2(List<TripRecord> db, float eps, int minPts){
        // do in constructor
        int[] label = new int[db.size()];
        int c = 0;
        for(TripRecord p : db){
            int i = db.indexOf(p);
            if(label[i] != 0) continue;

            List<TripRecord> n = regionQry(p, eps);
            if(n.size() < minPts) {
                label[i] = -1; // -1 defines noise
                continue;
            }

            Cluster cluster = new Cluster(++c);
            n.remove(p);
            List<TripRecord> seedSet = n;
            int size = seedSet.size();
            for(int k=0; k<size; k++){
                TripRecord q = seedSet.get(k);
                int j = db.indexOf(q);

                if(label[j] == -1) {
                    label[j] = c;
                    cluster.addGPS(q);
                }
                if(label[j] != 0) continue;
                label[j] = c;
                cluster.addGPS(q);
                n = regionQry(q, eps);
                if(n.size() >= minPts){
                    seedSet.addAll(n);
                    size += n.size();
                }
            }
            cluster.setCenter();
            this.clusters.add(cluster);
        }
    }

    /**
     * Method for expanding and adding points to a cluster
     * @param p
     * Central point of cluster to expand.
     * @param neighbours
     * Arraylist containing all neighbour points to @p
     * @param c
     * The specific cluster to expand/add points to
     * @param eps
     * The maximum distance that can be between points in order for them to still be
     * considered in the same cluster.
     * @param minPts
     * The minimum number of points that need to be grouped up in order for the algorithm
     * to consider that group a cluster.
     */
    private void expandCluster(TripRecord p, List<TripRecord> neighbours, Cluster c, float eps, int minPts) {
        c.addGPS(p);
        int size = neighbours.size();
        for(int i=0; i<size; i++){
            TripRecord tr = neighbours.get(i);
            if(tr.getCluster() == -1) c.addGPS(tr);

            if(!(tr.getVisited())){
                tr.visit();
                List<TripRecord> nPts = regionQry(tr, eps);
                if(nPts.size() >= minPts){
                    neighbours.addAll(nPts);
                    size+=nPts.size();
                }
            }
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
    private List<TripRecord> regionQry(TripRecord p, float eps){
        List<TripRecord> n = new ArrayList<>();
        for(TripRecord tr : this.tripRecords){
            if( euclidDist(p.getPickup_Location(), tr.getPickup_Location()) <= eps && tr!=p){
                n.add(tr);
            }
        }
        return n;
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
            //String[] attr = br.readLine().split(",");

            String line;
            br.readLine(); // skips first line with attributes
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
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
