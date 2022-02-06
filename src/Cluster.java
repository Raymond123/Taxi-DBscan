import java.util.ArrayList;

/**
 * @author eastonsmith
 *
 * describe class/object
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
