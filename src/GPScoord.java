public class GPScoord {

    float lat, lon;

    public GPScoord(float lon, float lat){
        this.lat = lat;
        this.lon = lon;
    }

    public float getDistance(GPScoord p){
        return this.lat - p.getLat();
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }
}
