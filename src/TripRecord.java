/**
 * @author eastonsmith
 *
 * This class is for storing critical data from each entry read from the taxi .csv file
 * Each entry is in reference to a specific trip the taxi made, hence the class' name.
 * This class stores the critical information as objects, and also has access methods for each instances attributes.
 * there are also a few methods for editing the attributes values.
 */
public class TripRecord {

    protected String pickup_DateTime;
    protected GPScoord pickup_Location;
    protected GPScoord dropoff_Location;
    protected float trip_Distance;

    protected Boolean visited;
    protected Boolean noise;
    protected int cluster;

    /**
     * TripRecord object constructor
     * @param pickup_dt
     * the date_time when the taxi picked up the customer for this specific trip
     * @param pickup_l
     * The GPScoord of where the taxi picked up the customer
     * @param dropoff_l
     * The GPScoord of where the taxi dropped off the customer
     * @param dist
     * The distance between pickup_l and dropoff_l, according to the the entry in the tripdata csv
     */
    public TripRecord(String pickup_dt, GPScoord pickup_l, GPScoord dropoff_l, float dist){
        this.pickup_DateTime = pickup_dt;
        this.pickup_Location = pickup_l;
        this.dropoff_Location = dropoff_l;
        this.trip_Distance = dist;

        this.visited = false;
        this.noise = false;
        this.cluster = -1;
    }

    // Methods for changing attributes of TripRecord
    public void noise() { this.noise = true; }
    public void visit() {
        this.visited = true;
    }
    public void inCluster(int c) { this.cluster = c; }
    public int getCluster() { return this.cluster; }

    // Access methods
    public Boolean getNoise() {
        return this.noise;
    }
    public Boolean getVisited() {
        return this.visited;
    }
    public float getTrip_Distance() {
        return this.trip_Distance;
    }
    public String getPickup_DateTime() {
        return this.pickup_DateTime;
    }
    public GPScoord getPickup_Location() {
        return this.pickup_Location;
    }
    public GPScoord getDropoff_Location() {
        return this.dropoff_Location;
    }
}
