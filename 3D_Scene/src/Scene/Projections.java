package Scene;

public class Projections {

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;
    private static final int W = 3;

    public static Vector perspective(Vector v, Vector c, Vector t, Vector e) {
        return perspective(new Matrix(v), c, t, e).col(0);
    }

    public static Matrix perspective(Matrix m, Vector c, Vector t, Vector e) {

        // Transform matrix m so that camera is now at origin
        Matrix cameraTransformX = new Matrix(new double[][] {
                {1, 0, 0},
                {0, Math.cos(-t.get(X)), -Math.sin(-t.get(X))},
                {0, Math.sin(-t.get(X)), Math.cos(-t.get(X))}
        });

        Matrix cameraTransformY = new Matrix(new double[][] {
                {Math.cos(-t.get(Y)), 0, Math.sin(-t.get(Y))},
                {0, 1, 0},
                {-Math.sin(-t.get(Y)), 0, Math.cos(-t.get(Y))}
        });

        Matrix cameraTransformZ = new Matrix(new double[][] {
                {Math.cos(-t.get(Z)), -Math.sin(-t.get(Z)), 0},
                {Math.sin(-t.get(Z)), Math.cos(-t.get(Z)), 0},
                {0, 0, 1}
        });

        Vector[] _c = new Vector[m.colCount()];
        for (int i=0; i<m.colCount(); i++) {
            _c[i] = c;
        }
        Matrix cMat = new Matrix(_c);

        Matrix d = cameraTransformX.multiply(cameraTransformY)
                .multiply(cameraTransformZ)
                .multiply(m.subtract(cMat));

        // Add an extra dimension to d
        // to convert to homogeneous coordinates
        double[][] _d = new double[d.rowCount()+1][d.colCount()];
        for (int i=0; i<d.rowCount(); i++) {
            for (int j=0; j<d.colCount(); j++) {
                _d[i][j] = d.get(i, j);
            }
        }
        for (int j=0; j<d.colCount(); j++) {
            _d[_d.length-1][j] = 1;
        }
        d = new Matrix(_d);

        // Project d into x/y plane
        Matrix projMat = new Matrix(new double[][] {
                {1, 0, -e.get(X)/e.get(Z), 0},
                {0, 1, -e.get(Y)/e.get(Z), 0},
                {0, 0, 1, 0},
                {0, 0, 1/e.get(Z), 0}
        });

        Matrix f = projMat.multiply(d);

        double[][] _b = new double[2][f.colCount()];
        for (int j=0; j<f.colCount(); j++) {
            _b[X][j] = f.get(X, j) / f.get(W, j);
            _b[Y][j] = f.get(Y, j) / f.get(W, j);
        }
        Matrix b = new Matrix(_b);

        return b;
    }

}
