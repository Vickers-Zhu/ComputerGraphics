package cube;

import acm.graphics.GLine;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;

public class Cylinder {

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    private double degree;

    private Vector[] vertices;

    public Cylinder(Vector v, double radius, double height) {
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);

//        vertices = new Vector[]{
//                new Vector(x0 + radius * Math.cos(Math.toRadians(degree)),
//                        y0 + radius * Math.sin(Math.toRadians(degree)), z0 + 0),
//                new Vector(x0 + radius * Math.cos(Math.toRadians(degree)),
//                        y0 + Math.sin(Math.toRadians(degree)), z0 + height)
//        };
    }

    public Matrix toMatrix() {
        return new Matrix(vertices);
    }

    public GLine[] toLines(Vector c, Vector t, Vector e) {
        Matrix projection = Projections.perspective(toMatrix(), c, t, e);
        Vector[] p = projection.cols();

//        GLine line01 = new GLine(p[0].get(X), p[0].get(Y), p[1].get(X), p[1].get(Y));

    }
    public static Vector[] generalCircle(Vector v, double R)
    {
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);
        java.util.Vector<Point2D.Double> points = new java.util.Vector<>();

        double dE = 3;
        double dSE = 5-2*R;
        double d = 1-R;
        double x = 0;
        double y = R;
        points.add(new Point2D.Double(x + x0, y + y0));
        points.add(new Point2D.Double(x - x0, y + y0));
        points.add(new Point2D.Double(x + x0, y - y0));
        points.add(new Point2D.Double(x - x0, y - y0));

        while (y > x)
        {
            if ( d < 0 ) //move to E
            {
                d += dE;
                dE += 2;
                dSE += 2;
            }
            else //move to SE
            {
                d += dSE;
                dE += 2;
                dSE += 4;
                --y;
            }
            ++x;
            points.add(new Point2D.Double(x + x0, y + y0));
            points.add(new Point2D.Double(-x + x0, y + y0));
            points.add(new Point2D.Double(x + x0, -y + y0));
            points.add(new Point2D.Double(-x + x0, -y + y0));
            points.add(new Point2D.Double(y + x0, x + y0));
            points.add(new Point2D.Double(-y + x0, x + y0));
            points.add(new Point2D.Double(y + x0, -x + y0));
            points.add(new Point2D.Double(-y + x0, -x + y0));
        }
        vertices = new Vector[]{
                for(Point2D.Double p : points){
                    new Vector(p.x, p.y, 0);
                }
        }

    }
}
