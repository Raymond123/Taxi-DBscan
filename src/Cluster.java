import java.util.ArrayList;

public class Cluster {

    protected int id;
    protected ArrayList<GPScoord> pntNum;
    protected GPScoord center;

    public Cluster(int id) {
        this.id = id;
        this.pntNum = new ArrayList<>();
    }

    public void setCenter(){
        this.center = getAvgGPS();
    }

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

    public void printClust(){
        System.out.printf("%d | %.10f | %.10f | %d", this.getId(), this.center.getLon(), this.center.getLat(), this.pntNum.size());
        System.out.println();
    }

    public int getId() {
        return id;
    }

    public GPScoord getCenter() { return center; }

    public int getPntNum() {
        return pntNum.size();
    }

}
