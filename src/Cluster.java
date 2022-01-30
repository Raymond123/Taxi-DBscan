import java.util.ArrayList;

public class Cluster {

    protected int id;
    protected ArrayList<GPScoord> pntNum;

    public Cluster(int id, ArrayList<GPScoord> points) {
        this.id = id;
        this.pntNum = points;
    }

    public void addGPS(GPScoord c) {
        this.pntNum.add(c);
    }

    public void printClust(){
        System.out.println(this.id + "| position" +" | " + this.pntNum.size());
    }

    public int getId() {
        return id;
    }

    public int getPntNum() {
        return pntNum.size();
    }

}
