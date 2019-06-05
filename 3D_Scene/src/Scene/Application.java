package Scene;

import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import com.google.gson.Gson;
import java.io.*;

public class Application extends GraphicsProgram {

    // For laziness
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    //Basic screen settings
    public static final int APPLICATION_WIDTH = 1280;
    public static final int APPLICATION_HEIGHT = 720;

    // Default viewing parameters
    private static final Vector c = new Vector(0, 300, -800);
    private static final Vector t = new Vector(0, 0, 0);
    private static final Vector e = new Vector(0, -300, 800);

    // v0 of two cubes
    private static final Vector x = new Vector(-100, 0, 100);
    private static final Vector y = new Vector(100, 0, -100);

    // Two cubes
    private static final Cube cubeX = new Cube(x, 200);
    private static final Cube cubeY = new Cube(y, 100);
    private static final Cylinder cylinderX = new Cylinder(y, 50, 200, 5);
    private static final Cone coneY = new Cone(x, 100, 400, 5);
    private static final Sphere sphereX = new Sphere(x, 100, 5, 5);

    // Camera pan mode settings
    private static final int CAMERA_PAN = 300;

    // 360 rotate mode settings
    private static final Vector mid = new Vector(0, 0, 0);
    private static final double r = 800;

    // Common graphic settings
    private static final int DELAY = 15;     // ms

    //start our Recurse objects
    public static void main(String[] args) {
//        new Application().start(args);
//        Gson gson = new Gson();
//        String str = gson.toJson(coneY);
//        System.out.println(str);

        loadFromFile();
    }

    public void run() {
//        while(true) {
////            cameraPanY();
//            rotateXZ();
//        }
        normalDisplay();
    }

    private static void loadFromFile(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/Scene/Scene"));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
            }
            System.out.println(str);
        } catch (IOException e) { }
    }

    private void normalDisplay() {
        removeAll();
        draw(cubeX, c, t, e);
        draw(cubeY, c, t, e);
        draw(cylinderX, c, t, e);
        draw(coneY, c, t, e);
        draw(sphereX, c, t, e);
        sleep();

    }

    private void cameraPanX() {
        for (int i=0; i<CAMERA_PAN; i++) {
            Vector _c = new Vector(c.get(X)+i, c.get(Y), c.get(Z));
            removeAll();
            draw(cubeX, _c, t, e);
            draw(cubeY, _c, t, e);
            sleep();
        }
        for (int i=CAMERA_PAN; i>-CAMERA_PAN; i--) {
            Vector _c = new Vector(c.get(X)+i, c.get(Y), c.get(Z));
            removeAll();
            draw(cubeX, _c, t, e);
            draw(cubeY, _c, t, e);
            sleep();
        }
        for (int i=-CAMERA_PAN; i<0; i++) {
            Vector _c = new Vector(c.get(X)+i, c.get(Y), c.get(Z));
            removeAll();
            draw(cubeX, _c, t, e);
            draw(cubeY, _c, t, e);
            sleep();
        }
    }

    private void cameraPanY() {
        for (int i=200; i<CAMERA_PAN; i++) {
            Vector _c = new Vector(c.get(X), c.get(Y)+i, c.get(Z));
            removeAll();
            draw(cubeX, _c, t, e);
            draw(cubeY, _c, t, e);
            sleep();
        }
        for (int i=CAMERA_PAN; i>-CAMERA_PAN; i--) {
            Vector _c = new Vector(c.get(X), c.get(Y)+i, c.get(Z));
            removeAll();
            draw(cubeX, _c, t, e);
            draw(cubeY, _c, t, e);
            sleep();
        }
        for (int i=-CAMERA_PAN; i<200; i++) {
            Vector _c = new Vector(c.get(X), c.get(Y)+i, c.get(Z));
            removeAll();
            draw(cubeX, _c, t, e);
            draw(cubeY, _c, t, e);
            sleep();
        }
    }

    private void cameraPanZ() {
        Vector _t = new Vector(t.get(X), -Math.toRadians(90), t.get(Z));
        for (int i=0; i<CAMERA_PAN; i++) {
            Vector _c = new Vector(c.get(X)+r, c.get(Y), i);
            removeAll();
            draw(cubeX, _c, _t, e);
            draw(cubeY, _c, _t, e);
            sleep();
        }
        for (int i=CAMERA_PAN; i>-CAMERA_PAN; i--) {
            Vector _c = new Vector(c.get(X)+r, c.get(Y), i);
            removeAll();
            draw(cubeX, _c, _t, e);
            draw(cubeY, _c, _t, e);
            sleep();
        }
        for (int i=-CAMERA_PAN; i<0; i++) {
            Vector _c = new Vector(c.get(X)+r, c.get(Y), i);
            removeAll();
            draw(cubeX, _c, _t, e);
            draw(cubeY, _c, _t, e);
            sleep();
        }
    }

    private void rotateXZ() {
        for (int i=0; i<360; i++) {
            Vector _c = new Vector(
                    mid.get(X)+r*Math.sin(Math.toRadians(i)),
                    c.get(Y),
                    mid.get(Z)-r*Math.cos(Math.toRadians(i))
            );
            Vector _t = new Vector(0, -Math.toRadians(i), 0);
            removeAll();
            draw(cubeX, _c, _t, e);
            draw(cubeY, _c, _t, e);
            sleep();
        }
    }

    private void rotateYZ() {
        for (int i=0; i<360; i++) {
            Vector _c = new Vector(
                    c.get(X),
                    mid.get(Y)+r*Math.sin(Math.toRadians(i)),
                    mid.get(Z)-r*Math.cos(Math.toRadians(i))
            );
            Vector _t = new Vector(Math.toRadians(i), 0, 0);
            removeAll();
            draw(cubeX, _c, _t, e);
            draw(cubeY, _c, _t, e);
            sleep();
        }
    }

    private void draw(Cube cube, Vector c, Vector t, Vector e) {
        GLine[] lines = cube.toLines(c, t, e);
        drawLines(lines);
    }

    private void draw(Cylinder cylinder, Vector c, Vector t, Vector e) {
        GLine[] lines = cylinder.toLines(c, t, e);
        drawLines(lines);
    }

    private void draw(Cone cone, Vector c, Vector t, Vector e){
        GLine[] lines = cone.toLines(c, t, e);
        drawLines(lines);
    }

    private void draw(Sphere sphere, Vector c, Vector t, Vector e){
        GLine[] lines = sphere.toLines(c, t, e);
        drawLines(lines);
    }

    private void drawLines(GLine[] lines) {
        for (GLine line : lines) {
            drawLine(line);
        }
    }

    private void drawLine(GLine line) {
        line.setStartPoint(
                line.getStartPoint().getX() + APPLICATION_WIDTH/2,
                -line.getStartPoint().getY() + APPLICATION_HEIGHT/2
        );
        line.setEndPoint(
                line.getEndPoint().getX() + APPLICATION_WIDTH/2,
                -line.getEndPoint().getY() + APPLICATION_HEIGHT/2
        );
        add(line);
    }

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

}
