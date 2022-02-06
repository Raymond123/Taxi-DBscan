/**
 * @author eastonsmith
 *
 * describe class/object
 */
public class GPScoord {

    float lat, lon;

    /**
     * Constructor for creating a GPS coordinate object
     * @param lon
     * longitude of the gps coordinate
     * @param lat
     * latitude of the gps coordinate
     */
    public GPScoord(float lon, float lat){
        this.lat = lat;
        this.lon = lon;
    }

    // Access methods
    public float getLat() {
        return lat;
    }
    public float getLon() {
        return lon;
    }
}
