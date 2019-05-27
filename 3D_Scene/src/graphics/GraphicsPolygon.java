package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsPolygon extends Polygon implements GraphicsShape {

    public GraphicsPolygon(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }

    @Override
    public void drawToImage(BufferedImage image) {
        System.out.println("Drawing shape to image");
        for (int x=0; x<image.getHeight(); x++) {
            for (int y=0; y<image.getWidth(); y++) {
                if (contains(x, y)) {
                    image.setRGB(x, y, 0xff33b5e5);
                }
            }
        }
    }
}
