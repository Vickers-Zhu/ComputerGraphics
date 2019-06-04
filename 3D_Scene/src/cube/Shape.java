package cube;

import acm.graphics.GLine;

public class Shape {
    protected static final int X = 0;
    protected static final int Y = 1;
    protected static final int Z = 2;

    public static Vector[] implement(Vector[] vertrices, Vector... arr){
        Vector[] b = new Vector[vertrices.length + arr.length];
        System.arraycopy(vertrices, 0, b, 0, vertrices.length);
        for(int i = 0; i < arr.length; i++) {
            b[i + vertrices.length] = arr[i];
        }
        return b;
    }

    public static GLine[] implement(GLine[] vertrices, GLine... arr){
        GLine[] b = new GLine[vertrices.length + arr.length];
        System.arraycopy(vertrices, 0, b, 0, vertrices.length);
        for(int i = 0; i < arr.length; i++) {
            b[i + vertrices.length] = arr[i];
        }
        return b;
    }

    protected int meshCount(double angle){
        return (int) (360 / angle);
    }

}
