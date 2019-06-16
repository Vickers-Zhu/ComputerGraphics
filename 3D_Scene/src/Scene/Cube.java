package Scene;
import acm.graphics.GLine;

public class Cube extends Shape{
    private Vector[] vertices;

    public Cube(Vector v, Vector oura, double side) {
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);
        vertices = new Vector[] {
                new Vector(x0, y0, z0),
                new Vector(x0+side, y0, z0),
                new Vector(x0+side, y0, z0+side),
                new Vector(x0, y0, z0+side),
                new Vector(x0, y0+side, z0),
                new Vector(x0+side, y0+side, z0),
                new Vector(x0+side, y0+side, z0+side),
                new Vector(x0, y0+side, z0+side)
        };

        Vector[][] rot = new Vector[1][0];
        rot[0] = implement(rot[0], vertices);
        rot = rotate(rot, v, ouraQuaternion(oura.get(X), oura.get(Y), oura.get(Z)));
        vertices = rot[0];
    }

    public Matrix toMatrix() {
        return new Matrix(vertices);
    }

    public GLine[] toLines(Vector c, Vector t, Vector e) {

        Matrix projection = Projections.perspective(toMatrix(), c, t, e);

        Vector[] p = projection.cols();

        GLine line01 = new GLine(p[0].get(X), p[0].get(Y), p[1].get(X), p[1].get(Y));
        GLine line12 = new GLine(p[1].get(X), p[1].get(Y), p[2].get(X), p[2].get(Y));
        GLine line23 = new GLine(p[2].get(X), p[2].get(Y), p[3].get(X), p[3].get(Y));
        GLine line30 = new GLine(p[3].get(X), p[3].get(Y), p[0].get(X), p[0].get(Y));
        GLine line45 = new GLine(p[4].get(X), p[4].get(Y), p[5].get(X), p[5].get(Y));
        GLine line56 = new GLine(p[5].get(X), p[5].get(Y), p[6].get(X), p[6].get(Y));
        GLine line67 = new GLine(p[6].get(X), p[6].get(Y), p[7].get(X), p[7].get(Y));
        GLine line74 = new GLine(p[7].get(X), p[7].get(Y), p[4].get(X), p[4].get(Y));
        GLine line04 = new GLine(p[0].get(X), p[0].get(Y), p[4].get(X), p[4].get(Y));
        GLine line15 = new GLine(p[1].get(X), p[1].get(Y), p[5].get(X), p[5].get(Y));
        GLine line26 = new GLine(p[2].get(X), p[2].get(Y), p[6].get(X), p[6].get(Y));
        GLine line37 = new GLine(p[3].get(X), p[3].get(Y), p[7].get(X), p[7].get(Y));

        return new GLine[] {
                line01, line12, line23, line30,
                line45, line56, line67, line74,
                line04, line15, line26, line37
        };

    }

}
