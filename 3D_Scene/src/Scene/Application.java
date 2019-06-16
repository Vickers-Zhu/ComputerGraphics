package Scene;

import acm.graphics.GLine;
import acm.program.GraphicsProgram;
import com.google.gson.Gson;
import java.io.*;
import javax.script.*;

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
    private static final Cube cubeX = new Cube(x, new Vector(0, 0, 0), 200);
    private static final Cube cubeY = new Cube(y, new Vector(0, 0, 0), 100);
    private java.util.Vector<Cube> cubes = new java.util.Vector<>();
    private java.util.Vector<Cone> cones = new java.util.Vector<>();
    private java.util.Vector<Cylinder> cylinders = new java.util.Vector<>();
    private java.util.Vector<Sphere> spheres = new java.util.Vector<>();


    // Camera pan mode settings
    private static final int CAMERA_PAN = 300;

    // 360 rotate mode settings
    private static final Vector mid = new Vector(0, 0, 0);
    private static final double r = 800;

    // Common graphic settings
    private static final int DELAY = 15;     // ms

    //start our Recurse objects
    public static void main(String[] args) {
        new Application().start(args);
    }

    public void run() {
        loadFromFile();
        while(true) {
//            cameraPanY();
            rotateXZ();
//            rotateYZ();
        }
//        normalDisplay();
    }

    private void loadFromFile(){
        Gson gson = new Gson();
        JsonObject jb;
        try {
            BufferedReader in = new BufferedReader(new FileReader("./src/Scene/Scene"));
            String str;
            while ((str = in.readLine()) != null) {
                jb = gson.fromJson(str, JsonObject.class);
                if(jb.name.equals("cube")){
                    cubes.add(new Cube(new Vector(jb.x, jb.y, jb.z), new Vector(jb.pitch, jb.yaw, jb.roll), jb.radius));
                    draw(cubes.lastElement(), c, t, e);
                }
                if(jb.name.equals("cylinder")) {
                    cylinders.add(new Cylinder(new Vector(jb.x, jb.y, jb.z), new Vector(jb.pitch, jb.yaw, jb.roll), jb.radius, jb.height, jb.angle));
                    draw(cylinders.lastElement(), c, t, e);
                }
                if(jb.name.equals("cone")){
                    cones.add(new Cone(new Vector(jb.x, jb.y, jb.z), new Vector(jb.pitch, jb.yaw, jb.roll), jb.radius, jb.height, jb.angle));
                    draw(cones.lastElement(), c, t, e);
                }
                if(jb.name.equals("sphere")){
                    spheres.add(new Sphere(new Vector(jb.x, jb.y, jb.z), new Vector(jb.pitch, jb.yaw, jb.roll), jb.radius, jb.angle, jb.angleS));
                    draw(spheres.lastElement(), c, t, e);
                }
            }
        } catch (IOException e) { }
    }

    private void normalDisplay() {
        removeAll();
        draw(cubeX, c, t, e);
        draw(cubeY, c, t, e);
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
//            draw(cubeX, _c, _t, e);
//            draw(cubeY, _c, _t, e);
            if(!cubes.isEmpty()) cubes.forEach(cube -> draw(cube, _c, _t, e));
            if(!cylinders.isEmpty()) cylinders.forEach(cylinder -> draw(cylinder, _c, _t, e));
            if(!cones.isEmpty()) cones.forEach(cone -> draw(cone, _c, _t, e));
            if(!spheres.isEmpty()) spheres.forEach(sphere -> draw(sphere, _c, _t, e));

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
            if(!cubes.isEmpty()) cubes.forEach(cube -> draw(cube, _c, _t, e));
            if(!cylinders.isEmpty()) cylinders.forEach(cylinder -> draw(cylinder, _c, _t, e));
            if(!cones.isEmpty()) cones.forEach(cone -> draw(cone, _c, _t, e));
            if(!spheres.isEmpty()) spheres.forEach(sphere -> draw(sphere, _c, _t, e));
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
