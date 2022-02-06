import java.util.ArrayList;

/**
 * @author eastonsmith
 *
 * Creates a cluster object.
 * Creating each cluster as a class allows for storing a List of them, which makes creating large amounts of clusters
 * and accessing them much simpler.
 *
 * Each cluster instance has 3 attributes, id, an array list of all the points in the cluster, and the GPS coordinate of the
 * center of the cluster.
 * This class contains the necessary methods for finding the center of itself, and several access methods for accessing its
 * attributes. There is also a specific 'print' method for printing out the details of the specific cluster instance into the console.
 */

public class Cluster {

    protected int id;
    protected ArrayList<GPScoord> pntNum;
    protected GPScoord center;

    /**
     * Constructor for creating a cluster object
     * @param id
     * The cluster number to differentiate different clusters and the order they were created
     */
    public Cluster(int id) {
        this.id = id;
        this.pntNum = new ArrayList<>();
    }

    /**
     * sets the center of the cluster by finding the average of each
     * GPS coordinate that is in the cluster
     * @see this.center
      */
    public void setCenter(){
        this.center = getAvgGPS();
    }

    /**
     * Goes through all the TripRecords in the cluster and finds the average lat and lon
     * between the starting points
     * @return
     * GPS coordinate at the center of the cluster
     */
    private GPScoord getAvgGPS(){
        float avgLat = 0f;
        float avgLon = 0f;

        for(GPScoord gps : this.pntNum){
            avgLat+=gps.getLat();
            avgLon+=gps.getLon();
        }
        int n = this.pntNum.size();
        return new GPScoord(avgLon/n, avgLat/n);
    }

    public void addGPS(TripRecord p) {
        this.pntNum.add(p.getPickup_Location());
        p.inCluster(this.id);
    }

    // Prints the cluster in the format (id | longitude | latitude | size)
    public void printClust(){
        System.out.printf("%d | %.10f | %.10f | %d", this.getId(), this.center.getLon(), this.center.getLat(), this.pntNum.size());
        System.out.println();
    }

    // Access methods
    public int getId() {
        return id;
    }
    public GPScoord getCenter() {
        return center;
    }
    public int getPntNum() {
        return pntNum.size();
    }

}
