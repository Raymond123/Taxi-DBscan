public class Cluster {

    protected int id;
    protected GPScoord pos;
    protected int pntNum;

    public Cluster(int id, int points, GPScoord position){
        this.id = id;
        this.pntNum = points;
        this.pos = position;
    }

    public int getId() {
        return id;
    }

    public int getPntNum() {
        return pntNum;
    }

    public GPScoord getPos() {
        return pos;
    }
}
