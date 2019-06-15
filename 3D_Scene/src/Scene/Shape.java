package Scene;

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

    public static Matrix[] implement(Matrix[] vertrices, Matrix... arr){
        Matrix[] b = new Matrix[vertrices.length + arr.length];
        System.arraycopy(vertrices, 0, b, 0, vertrices.length);
        for(int i = 0; i < arr.length; i++) {
            b[i + vertrices.length] = arr[i];
        }
        return b;
    }
    private Vector rotate(Vector init, Quaternion quaternion){
        Quaternion point = new Quaternion(0, init.get(X), init.get(Y), init.get(Z));
        Quaternion result = quaternion.times(point.times(quaternion.inv()));
        return new Vector(result.get(1), result.get(2), result.get(3));
    }

    protected int meshCount(double angle){
        return (int) (360 / angle);
    }

    public Vector[][] rotate(Vector[][] origin, Vector zeros, Quaternion quaternion){
        if(quaternion.norm() != 1) return null;
        for(int r = 0; r < origin.length; r++){
            for(int c = 0; c < origin[0].length; c++){
                origin[r][c].set(X, origin[r][c].get(X) - zeros.get(X));
                origin[r][c].set(Y, origin[r][c].get(Y) - zeros.get(Y));
                origin[r][c].set(Z, origin[r][c].get(Z) - zeros.get(Z));

                Vector rot = rotate(origin[r][c], quaternion);
                origin[r][c].set(X, rot.get(X));
                origin[r][c].set(Y, rot.get(Y));
                origin[r][c].set(Z, rot.get(Z));

                origin[r][c].set(X, origin[r][c].get(X) + zeros.get(X));
                origin[r][c].set(Y, origin[r][c].get(Y) + zeros.get(Y));
                origin[r][c].set(Z, origin[r][c].get(Z) + zeros.get(Z));
            }
        }
        return origin;
    }
}
