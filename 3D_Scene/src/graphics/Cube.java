package graphics;

import static graphics.Constants.*;


public class Cube {

    private Vector[] vertices;

    public Vector[] getVertices() {
        return vertices;
    }

    public Cube(Vector v, double side) {
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
    }

}
