package cube;

import acm.graphics.GLine;

public class Sphere extends Shape{
    private double radius;
    private double angleS;
    private double angleD;

    private Vector[][] vetrices;

    public Sphere(Vector v, double radius, double angleD, double angleS){
        this.angleD = angleD;
        this.angleS = angleS;
        this.radius = radius;
        int meshcount = meshCount(angleD);
        vetrices = new Vector[meshcount][0];
        initPoints(v);
    }

    private void initPoints(Vector v){
        int vercount = meshCount(angleD);
        int horcount = (int) Math.round(180 / angleS)+1;
        for(int i = 0; i < vercount; i++){
            for(int j = 0; j < horcount; j++){
                vetrices[i] = implement(vetrices[i], new Vector(
                        v.get(X) + radius * Math.sin(Math.toRadians(j*angleS)) * Math.cos(Math.toRadians(i*angleD)),
                        v.get(Y) + radius * Math.sin(Math.toRadians(j*angleS)) * Math.sin(Math.toRadians(i*angleD)),
                        v.get(Z) + radius * Math.cos(Math.toRadians(j*angleS))));
            }
        }
    }

    private Matrix[] toMatrices(){
        Matrix[] matrices = new Matrix[0];
        int meshcount = meshCount(angleD);
        for(int i = 0; i < meshcount; i++){
            matrices = implement(matrices, new Matrix(vetrices[i]));
        }
        return matrices;
    }

    public GLine[] toLines(Vector c, Vector t, Vector e){
        Matrix[] matrices = toMatrices();
        int vercount = meshCount(angleD);
        int horcount = (int) Math.round(180 / angleS);
        Vector[][] vertrices = new Vector[vercount][0];
        GLine[] lines = new GLine[0];
        for(int i = 0; i < vercount; i++){
            vertrices[i] = implement(vertrices[i], Projections.perspective(matrices[i], c, t, e).cols());
        }
        for(int i = 0; i < vercount; i++){
            for(int j = 0; j < horcount; j++){
                lines = implement(lines, new GLine(
                    vertrices[i][j].get(X), vertrices[i][j].get(Y),
                    vertrices[i][j+1].get(X), vertrices[i][j+1].get(Y)
                ));
            }
        }
        for(int j = 1; j < horcount; j++){
            lines = implement(lines, new GLine(
                vertrices[vercount-1][j].get(X), vertrices[vercount-1][j].get(Y),
                vertrices[0][j].get(X), vertrices[0][j].get(Y)
            ));
            for(int i = 0; i < vercount-1; i++){
                lines = implement(lines, new GLine(
                        vertrices[i][j].get(X), vertrices[i][j].get(Y),
                        vertrices[i+1][j].get(X), vertrices[i+1][j].get(Y)
                ));
            }
        }
        return lines;
    }
}
