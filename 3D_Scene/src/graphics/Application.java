package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static graphics.Constants.*;

public class Application extends JFrame {

    public static class ImagePanel extends JPanel {

        private Image mImage;

        public ImagePanel(Image image) {
            mImage = image;
        }

        public void setImage(Image image) {
            System.out.println("Setting image");
            mImage = image;
            revalidate();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(mImage, 0, 0, null);
        }

    }

    //Basic screen settings
    public static final int CANVAS_WIDTH = 400;
    public static final int CANVAS_HEIGHT = 400;

    public static final double SCREEN_RADIUS = Math.sqrt(Math.pow(CANVAS_WIDTH/2, 2) + Math.pow(CANVAS_HEIGHT/2, 2));

    public static final long DELAY = 5;

    private ImagePanel mPanel;

    public Application() {
        initUI();
    }

    private void initUI() {

        BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        mPanel = new ImagePanel(image);
        getContentPane().add(mPanel);

        setTitle("Simple Example");
        setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setImage(Image image) {
        mPanel.setImage(image);
    }

    public static void main(String[] args) throws InterruptedException {
        final Application app = new Application();
        EventQueue.invokeLater(() -> app.setVisible(true));
        app.doStuff();
    }

    private static final Vector v0 = new Vector(0, 0, 500);
    private static final Vector v1 = new Vector(-500, 0, 500);
    private static final double side = 500;

    private static final double RENDER_DIST = 500;

    private static final double CAMERA_DIST = 50;

    private void doStuff() {

        Cube[] cubes = new Cube[] {
                new Cube(v0, side),
                new Cube(v1, side)
        };

        Vector observer = new Vector(0, 0, 0);
        Vector normal = new Vector(0, 0, 1);
        Matrix rotTrans = getRotationTransformMatrix(normal);

        List<RenderQueueItem> renderQueue = new ArrayList<>();

        for (Cube cube : cubes) {

            // Scan every vertex
            // Calculate distances + find closest
            double distToClosestVertex = RENDER_DIST + 1;
            int closestVertexIndex = -1;
            Vector[] relPos = new Vector[8];    // Position of vertex relative to observer
            double[] absDist = new double[8];  // Magnitude of relative position projected onto normal
            boolean inView = false;

            for (int i=0; i<8; i++) {
                Vector vertex = cube.getVertices()[i];
                relPos[i] = vertex.subtract(observer);
                absDist[i] = relPos[i].dot(normal);
                if (!inView && absDist[i] > CAMERA_DIST && absDist[i] <= RENDER_DIST) {
                    inView = true;
                }
                if (inView) {
                    if (absDist[i] < distToClosestVertex) {
                        distToClosestVertex = absDist[i];
                        closestVertexIndex = i;
                    }
                }
            }

            if (!inView) {
                continue;
            }

            Vector[] screenPos = new Vector[8]; // Vector projected onto screen
            boolean onScreen = false;

            for (int i = 0; i < 8; i++) {
                screenPos[i] = relPos[i]
                        .subtract(normal.multiply(absDist[i]))
                        .multiply(CAMERA_DIST / absDist[i]);
                if (!onScreen && screenPos[i].magnitude() <= SCREEN_RADIUS) {
                    onScreen = true;
                }
            }

            if (!onScreen) {
                continue;
            }

            Matrix m = new Matrix(screenPos);
            m = rotTrans.multiply(m);
            screenPos = m.cols();
            Point[] points = new Point[8];
            for (int i=0; i<8; i++) {
                Vector v = screenPos[i].subtract(observer);
                points[i] = new Point((int) (v.get(X) + CANVAS_WIDTH/2), (int) (-v.get(Y) + CANVAS_HEIGHT/2));
            }

            int[][] surfaces;

            switch (closestVertexIndex) {
                case 0:
                    surfaces = new int[][] {
                            new int[] {0, 1, 2, 3},
                            new int[] {0, 1, 5, 4},
                            new int[] {0, 3, 7, 4}
                    };
                    break;
                case 1:
                    surfaces = new int[][] {
                            new int[] {1, 2, 6, 5},
                            new int[] {1, 5, 4, 0},
                            new int[] {1, 2, 3, 0}
                    };
                    break;
                case 2:
                    surfaces = new int[][] {
                            new int[] {2, 6, 5, 1},
                            new int[] {2, 3, 0, 1},
                            new int[] {2, 6, 7, 3}
                    };
                    break;
                case 3:
                    surfaces = new int[][] {
                            new int[] {3, 2, 1, 0},
                            new int[] {3, 0, 4, 7},
                            new int[] {3, 2, 6, 7}
                    };
                    break;
                case 4:
                    surfaces = new int[][] {
                            new int[] {4, 5, 1, 0},
                            new int[] {4, 7, 3, 0},
                            new int[] {4, 5, 6, 7}
                    };
                    break;
                case 5:
                    surfaces = new int[][] {
                            new int[] {5, 6, 7, 4},
                            new int[] {5, 6, 2, 1},
                            new int[] {5, 4, 0, 1}
                    };
                    break;
                case 6:
                    surfaces = new int[][] {
                            new int[] {6, 5, 1, 2},
                            new int[] {6, 7, 3, 2},
                            new int[] {6, 7, 4, 5}
                    };
                    break;
                case 7:
                    surfaces = new int[][] {
                            new int[] {7, 6, 5, 4},
                            new int[] {7, 6, 2, 3},
                            new int[] {7, 3, 0, 4}
                    };
                    break;
                default:
                    System.out.println("ERROR!!!!!!!!!!!!!! YOU SCREWED UP!!!!! YOU ARE BAD!!!!!!!!");
                    continue;
            }

            for (int[] surface : surfaces) {
                renderQueue.add(new RenderQueueItem(distToClosestVertex,
                        points[surface[0]],
                        points[surface[1]],
                        points[surface[2]],
                        points[surface[3]]
                ));
            }

        }

        Collections.sort(renderQueue, (a, b) -> Double.valueOf(b.depth).compareTo(a.depth));
        BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //g.drawString("Hello", 50, 50);
        for (RenderQueueItem item : renderQueue) {
            System.out.println(Arrays.toString(item.polygon.xpoints) + ", " + Arrays.toString(item.polygon.ypoints));
            g.setColor(Color.WHITE);
            g.fillPolygon(item.polygon);
            g.setColor(new Color(0x33b5e5));
            g.drawPolygon(item.polygon);
        }
        /*Polygon p = new Polygon(
                new int[] {10, 30, 30, 10},
                new int[] {10, 10, 30, 30},
                4
        );
        g.drawPolygon(p);*/
        g.dispose();
        setImage(image);

    }

    public static Matrix getRotationTransformMatrix(Vector angle) {

        Vector t = angle.multiply(-1);

        Matrix cameraTransformX = new Matrix(new double[][] {
                {Math.cos(-t.get(X)), -Math.sin(-t.get(X)), 0},
                {Math.sin(-t.get(X)), Math.cos(-t.get(X)), 0},
                {0, 0, 1}
        });

        Matrix cameraTransformZ = new Matrix(new double[][] {
                {1, 0, 0},
                {0, Math.cos(-t.get(X)), -Math.sin(-t.get(X))},
                {0, Math.sin(-t.get(X)), Math.cos(-t.get(X))}
        });

        Matrix cameraTransformY = new Matrix(new double[][] {
                {Math.cos(-t.get(Y)), 0, Math.sin(-t.get(Y))},
                {0, 1, 0},
                {-Math.sin(-t.get(Y)), 0, Math.cos(-t.get(Y))}
        });

        return cameraTransformX.multiply(cameraTransformY).multiply(cameraTransformZ);
    }

    private void drawShape() {

        for (int i=0; i<200; i++) {

            Vector[] v = new Vector[] {
                    new Vector(20+i, 25),
                    new Vector(25+i, 25),
                    new Vector(30+i, 20),
                    new Vector(30+i, 15),
                    new Vector(25+i, 10),
                    new Vector(20+i, 10),
                    new Vector(15+i, 15),
                    new Vector(15+i, 20)
            };

            GraphicsPolygon polygon = new GraphicsPolygon(getXPoints(v), getYPoints(v), v.length);
            BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            polygon.drawToImage(image);
            setImage(image);

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static int[] getXPoints(Vector[] v) {
        int[] xpoints = new int[v.length];
        for (int i=0; i<v.length; i++) {
            xpoints[i] = (int) v[i].get(X);
        }
        return xpoints;
    }

    private static int[] getYPoints(Vector[] v) {
        int[] ypoints = new int[v.length];
        for (int i=0; i<v.length; i++) {
            ypoints[i] = (int) v[i].get(Y);
        }
        return ypoints;
    }

}
