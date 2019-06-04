package cube;

import acm.graphics.GLine;

public class Cone extends Shape{

    private double angle;
    private double radius;
    private double height;

    private Vector[] vertices;

    public Cone(Vector v, double radius, double height, double angle){
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);
        this.angle = angle;
        this.radius = radius;
        this.height = height;
        vertices = new Vector[]{
                new Vector(x0, y0, z0)
        };
        initPoints(v);
    }

    private Matrix toMatrix(){
        return new Matrix(vertices);
    }

    private void initPoints(Vector v){
        int meshcount = meshCount(angle);
        for(int i = 0; i < meshcount; i++){
            vertices = implement(vertices, new Vector(v.get(X) + radius*Math.cos(Math.toRadians(i*angle)),
                    v.get(Y) + radius*Math.sin(Math.toRadians(i*angle)), v.get(Z) + height));
        }
        System.out.println(vertices[0].get(Z));
        System.out.println(vertices[2].get(Z));
    }

    public GLine[] toLines(Vector c, Vector t, Vector e) {
        Matrix projection = Projections.perspective(toMatrix(), c, t, e);

        Vector[] p = projection.cols();
        GLine[] lines = new GLine[0];

        int meshcount = meshCount(angle);

        lines = implement(lines, new GLine(p[meshcount].get(X), p[meshcount].get(Y), p[1].get(X), p[1].get(Y)));
        lines = implement(lines, new GLine(p[meshcount].get(X), p[meshcount].get(Y), p[0].get(X), p[0].get(Y)));
        for(int i = 1; i < meshcount; i++){
            lines = implement(lines, new GLine(p[i].get(X), p[i].get(Y), p[i+1].get(X), p[i+1].get(Y)));
            lines = implement(lines, new GLine(p[i].get(X), p[i].get(Y), p[0].get(X), p[0].get(Y)));
        }
        return lines;
    }
}
