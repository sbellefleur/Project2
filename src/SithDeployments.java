public class SithDeployment extends Deployment{
    public SithDeployment(int id, long ts, int g, int p, int s, int f){
        super (id, ts, g, p, s, f, q);
    }

    @Override
    public boolean isSith() {
        return false;
    }

    @Override
    public int compareTo(Deployment o) {
        return 0;
    }
}

