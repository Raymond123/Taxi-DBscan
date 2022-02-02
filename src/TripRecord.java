public class TripRecord {

    protected String pickup_DateTime;
    protected GPScoord pickup_Location;
    protected GPScoord dropoff_Location;
    protected float trip_Distance;

    protected Boolean visited;
    protected Boolean noise;
    protected int cluster;

    public TripRecord(String pickup_dt, GPScoord pickup_l, GPScoord dropoff_l, float dist){
        this.pickup_DateTime = pickup_dt;
        this.pickup_Location = pickup_l;
        this.dropoff_Location = dropoff_l;
        this.trip_Distance = dist;

        this.visited = false;
        this.noise = false;
        this.cluster = -1;
    }

    public void noise() {this.noise = true;}
    public void visit() {
        this.visited = true;
    }
    public void inCluster(int c) {
        this.cluster = c;
    }
    public int getCluster() { return this.cluster; }
    public Boolean getNoise() { return this.noise; }
    public Boolean getVisited() { return this.visited; }
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
