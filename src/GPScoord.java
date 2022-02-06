/**
 * @author eastonsmith
 *
 * Class/Object for storing the location of a taxi using GPS coordinates
 * Only methods incluce the constructor and some access method.
 * Only attributes are the latitude and longitude of the GPS coordinate being created.
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
