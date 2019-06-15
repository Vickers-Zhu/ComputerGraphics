package Scene;

public class Quaternion extends Vector {
    private final int W = 0;
    private final int X = 1;
    private final int Y = 2;
    private final int Z = 3;
    public Quaternion(double w, double x, double y, double z){
        super(w, x, y, z);
    }

    public double norm(){
        return get(X)*get(X) + get(Y)*get(Y) + get(Z)*get(Z) + get(W)*get(W);
    }
    public Quaternion conjugate() {
        return new Quaternion(get(W), -get(X), -get(Y), -get(Z));
    }
    public Quaternion times(Quaternion quaternion){
        double w0 = get(W)*quaternion.get(W) - get(X)*quaternion.get(X) - get(Y)*quaternion.get(Y) - get(Z)*quaternion.get(Z);
        double x0 = get(W)*quaternion.get(X) + get(X)*quaternion.get(W) + get(Y)*quaternion.get(Z) - get(Z)*quaternion.get(Y);
        double y0 = get(W)*quaternion.get(Y) - get(X)*quaternion.get(Z) + get(Y)*quaternion.get(W) + get(Z)*quaternion.get(X);
        double z0 = get(W)*quaternion.get(Z) + get(X)*quaternion.get(Y) - get(Y)*quaternion.get(X) + get(Z)*quaternion.get(W);
        return new Quaternion(w0, x0, y0, z0);
    }
    public Quaternion inv(){
        Quaternion con = conjugate();
        double num = norm();
        double w0 = con.get(W)*num;
        double x0 = con.get(X)*num;
        double y0 = con.get(Y)*num;
        double z0 = con.get(Z)*num;
        return new Quaternion(w0, x0, y0, z0);
    }
}
