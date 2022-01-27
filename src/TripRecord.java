public class TripRecord {

    protected String pickup_DateTime;
    protected GPScoord pickup_Location;
    protected GPScoord dropoff_Location;
    protected float trip_Distance;

    public TripRecord(String pickup_dt, GPScoord pickup_l, GPScoord dropoff_l, float dist){
        this.pickup_DateTime = pickup_dt;
        this.pickup_Location = pickup_l;
        this.dropoff_Location = dropoff_l;
        this.trip_Distance = dist;

    }

    private float tripDist(GPScoord start, GPScoord end){

        return 0.0f;
    }


    public float getTrip_Distance() {
        return trip_Distance;
    }

    public String getPickup_DateTime() {
        return pickup_DateTime;
    }

    public GPScoord getPickup_Location() {
        return pickup_Location;
    }

    public GPScoord getDropoff_Location() {
        return dropoff_Location;
    }
}
