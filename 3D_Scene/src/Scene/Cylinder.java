package Scene;

import acm.graphics.GLine;

public class Cylinder extends Shape{

    private double angle;
    private double radius;
    private double height;

    private Vector[] verticesBottom;
    private Vector[] verticesTop;

    public Cylinder(Vector v, Vector oura, double radius, double height, double angle) {
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);
        this.radius = radius;
        this.height = height;
        this.angle = angle;
        verticesBottom = new Vector[0];
        verticesTop = new Vector[0];
        initPoints(v);
        Vector[][] rot = new Vector[2][0];
        rot[0] = implement(rot[0], verticesBottom);
        rot[1] = implement(rot[1], verticesTop);
        rot = rotate(rot, v, ouraQuaternion(oura.get(X), oura.get(Y), oura.get(Z)));
        verticesBottom = rot[0];
        verticesTop = rot[1];
    }

    public Matrix[] toMatrix() {
        return new Matrix[]{
                new Matrix(verticesBottom),
                new Matrix(verticesTop)
        };
    }

    private void initPoints(Vector v){
        int meshcount = meshCount(angle);
        for(int i = 0; i < meshcount; i++){
            verticesBottom = implement(verticesBottom, new Vector(
                    v.get(X) + radius * Math.cos(Math.toRadians(i * angle)),
                    v.get(Y) + radius * Math.sin(Math.toRadians(i * angle)), v.get(Z)));
            verticesTop = implement(verticesTop, new Vector(
                    v.get(X) + radius * Math.cos(Math.toRadians(i * angle)),
                    v.get(Y) + radius * Math.sin(Math.toRadians(i * angle)), v.get(Z) + height));
        }
    }

    public GLine[] toLines(Vector c, Vector t, Vector e) {
        Matrix projectionBottom = Projections.perspective(toMatrix()[0], c, t, e);
        Matrix projectionTop = Projections.perspective(toMatrix()[1], c, t, e);

        Vector[] bot = projectionBottom.cols();
        Vector[] top = projectionTop.cols();

        GLine[] lines = new GLine[0];

        int meshcount = meshCount(angle);

        lines = implement(lines, new GLine(bot[meshcount-1].get(X), bot[meshcount-1].get(Y), top[meshcount-1].get(X), top[meshcount-1].get(Y)));
        lines = implement(lines, new GLine(bot[meshcount-1].get(X), bot[meshcount-1].get(Y), bot[0].get(X), bot[0].get(Y)));
        lines = implement(lines, new GLine(top[meshcount-1].get(X), top[meshcount-1].get(Y), top[0].get(X), top[0].get(Y)));

        for(int i = 0; i < meshcount-1; i++){
            lines = implement(lines, new GLine(bot[i].get(X), bot[i].get(Y), top[i].get(X), top[i].get(Y)));
            lines = implement(lines, new GLine(bot[i].get(X), bot[i].get(Y), bot[i+1].get(X), bot[i+1].get(Y)));
            lines = implement(lines, new GLine(top[i].get(X), top[i].get(Y), top[i+1].get(X), top[i+1].get(Y)));
        }

        return lines;
    }
}
