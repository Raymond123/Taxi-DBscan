import java.util.ArrayList;

public class Cluster {

    protected int id;
    protected ArrayList<GPScoord> pntNum;
    protected GPScoord center;

    public Cluster(int id, GPScoord p) {
        this.id = id;
        this.pntNum = new ArrayList<>();
        this.center = p;
    }

    public void addGPS(TripRecord p) {
        this.pntNum.add(p.getPickup_Location());
        p.inCluster(this.id);
    }

    public void printClust(){
        System.out.println(this.id + "| " + this.center.getLon() + " | " + this.center.getLat() + " | " + this.pntNum.size());
    }

    public int getId() {
        return id;
    }

    public GPScoord getCenter() { return center; }

    public int getPntNum() {
        return pntNum.size();
    }

}
