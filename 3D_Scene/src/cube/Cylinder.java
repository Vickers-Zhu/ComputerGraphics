package cube;

import acm.graphics.GLine;

public class Cylinder {

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    private double degree;
    private double radius;
    private double height;

    private cube.Vector[] verticesBottom;
    private cube.Vector[] verticesTop;

    public Cylinder(Vector v, double radius, double height) {
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);
        this.radius = radius;
        this.height = height;
        verticesBottom = new Vector[0];
        verticesTop = new Vector[0];

    }

    public Matrix[] toMatrix() {
        return new Matrix[]{
                new Matrix(verticesBottom),
                new Matrix(verticesTop)
        };
    }

    public Vector[] implement(Vector[] vertrices, Vector... arr){
        Vector[] b = new Vector[vertrices.length + arr.length];
        System.arraycopy(vertrices, 0, b, 0, vertrices.length);
        for(int i = 0; i < arr.length; i++) {
            b[i + vertrices.length] = arr[i];
        }
        return b;
    }

    public GLine[] implement(GLine[] vertrices, GLine... arr){
        GLine[] b = new GLine[vertrices.length + arr.length];
        System.arraycopy(vertrices, 0, b, 0, vertrices.length);
        for(int i = 0; i < arr.length; i++) {
            b[i + vertrices.length] = arr[i];
        }
        return b;
    }

    public GLine[] toLines(Vector c, Vector t, Vector e) {
        Matrix projectionBottom = Projections.perspective(toMatrix()[0], c, t, e);
        Matrix projectionTop = Projections.perspective(toMatrix()[1], c, t, e);

        Vector[] bot = projectionBottom.cols();
        Vector[] top = projectionTop.cols();

        GLine[] lines = new GLine[0];
        GLine[] linesT = new GLine[0];
        for(int i = 0; i < bot.length / 8; i++){
            lines = implement(lines, new GLine(bot[i].get(X), bot[i].get(Y),
                    bot[i+1].get(X), bot[i+1].get(Y)));
            lines = implement(lines, new GLine(bot[i*2].get(X), bot[i*2].get(Y),
                    bot[i*2+1].get(X), bot[i*2+1].get(Y)));
            lines = implement(lines, new GLine(bot[i*3].get(X), bot[i*3].get(Y),
                    bot[i*3+1].get(X), bot[i*3+1].get(Y)));
            lines = implement(lines, new GLine(bot[i*4].get(X), bot[i*4].get(Y),
                    bot[i*4+1].get(X), bot[i*4+1].get(Y)));
            lines = implement(lines, new GLine(bot[i*5].get(X), bot[i*5].get(Y),
                    bot[i*5+1].get(X), bot[i*5+1].get(Y)));
            lines = implement(lines, new GLine(bot[i*6].get(X), bot[i*6].get(Y),
                    bot[i*6+1].get(X), bot[i*6+1].get(Y)));
            lines = implement(lines, new GLine(bot[i*7].get(X), bot[i*7].get(Y),
                    bot[i*7+1].get(X), bot[i*7+1].get(Y)));
            if(i*8+1 == bot.length)
                lines = implement(lines, new GLine(bot[i*8].get(X), bot[i*8].get(Y),
                        bot[0].get(X), bot[0].get(Y)));
            else
                lines = implement(lines, new GLine(bot[i*8].get(X), bot[i*8].get(Y),
                        bot[i*8+1].get(X), bot[i*8+1].get(Y)));
        }
        for(int i = 0; i < top.length / 8; i++){
            linesT = implement(linesT, new GLine(top[i].get(X), top[i].get(Y),
                    top[i+1].get(X), top[i+1].get(Y)));
            linesT = implement(linesT, new GLine(top[i*2].get(X), top[i*2].get(Y),
                    top[i*2+1].get(X), top[i*2+1].get(Y)));
            linesT = implement(linesT, new GLine(top[i*3].get(X), top[i*3].get(Y),
                    top[i*3+1].get(X), top[i*3+1].get(Y)));
            linesT = implement(linesT, new GLine(top[i*4].get(X), top[i*4].get(Y),
                    top[i*4+1].get(X), top[i*4+1].get(Y)));
            linesT = implement(linesT, new GLine(top[i*5].get(X), top[i*5].get(Y),
                    top[i*5+1].get(X), top[i*5+1].get(Y)));
            linesT = implement(linesT, new GLine(top[i*6].get(X), top[i*6].get(Y),
                    top[i*6+1].get(X), top[i*6+1].get(Y)));
            linesT = implement(linesT, new GLine(top[i*7].get(X), top[i*7].get(Y),
                    top[i*7+1].get(X), top[i*7+1].get(Y)));
            if(i*8+1 == top.length)
                linesT = implement(linesT, new GLine(top[i*8].get(X), top[i*8].get(Y),
                        top[0].get(X), top[0].get(Y)));
            else
                linesT = implement(linesT, new GLine(top[i*8].get(X), top[i*8].get(Y),
                        top[i*8+1].get(X), top[i*8+1].get(Y)));
        }

    return implement(lines, linesT);
//        GLine line01 = new GLine(p[0].get(X), p[0].get(Y), p[1].get(X), p[1].get(Y));
    }

    public void generalCircle(Vector v)
    {
        double x0 = v.get(X);
        double y0 = v.get(Y);
        double z0 = v.get(Z);
        double R = this.radius;
        double dE = 3;
        double dSE = 5-2*R;
        double d = 1-R;
        double x = 0;
        double y = R;
        verticesBottom = implement(verticesBottom, new Vector[]{
                new Vector(x + x0, y + y0, 0),
                new Vector(x - x0, y + y0, 0),
                new Vector(x + x0, y - y0, 0),
                new Vector(x - x0, y - y0, 0),
                new Vector(y + x0, x + y0, 0),
                new Vector(y - x0, x + y0, 0),
                new Vector(y + x0, x - y0, 0),
                new Vector(y - x0, x - y0, 0)
        });
        verticesTop = implement(verticesTop, new Vector[]{
                new Vector(x + x0, y + y0, height),
                new Vector(x - x0, y + y0, height),
                new Vector(x + x0, y - y0, height),
                new Vector(x - x0, y - y0, height),
                new Vector(y + x0, x + y0, height),
                new Vector(y - x0, x + y0, height),
                new Vector(y + x0, x - y0, height),
                new Vector(y - x0, x - y0, height)
        });

        while (y > x) {
            if (d < 0) //move to E
            {
                d += dE;
                dE += 2;
                dSE += 2;
            } else //move to SE
            {
                d += dSE;
                dE += 2;
                dSE += 4;
                --y;
            }
            ++x;
            verticesBottom = implement(verticesBottom, new Vector[]{
                    new Vector(x + x0, y + y0, 0),
                    new Vector(x - x0, y + y0, 0),
                    new Vector(x + x0, y - y0, 0),
                    new Vector(x - x0, y - y0, 0),
                    new Vector(y + x0, x + y0, 0),
                    new Vector(y - x0, x + y0, 0),
                    new Vector(y + x0, x - y0, 0),
                    new Vector(y - x0, x - y0, 0)
            });
            verticesTop = implement(verticesTop, new Vector[]{
                    new Vector(x + x0, y + y0, height),
                    new Vector(x - x0, y + y0, height),
                    new Vector(x + x0, y - y0, height),
                    new Vector(x - x0, y - y0, height),
                    new Vector(y + x0, x + y0, height),
                    new Vector(y - x0, x + y0, height),
                    new Vector(y + x0, x - y0, height),
                    new Vector(y - x0, x - y0, height)
            });
        }
    }
}
