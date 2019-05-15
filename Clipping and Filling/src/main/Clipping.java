package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Clipping extends JPanel{
	private int width;
	private int height;	
	private boolean isBoundaryLine = false;
    protected BufferedImage canvas;
    protected Vector<Point> pointlist;
    protected Vector<Point> clipBoundary;

    public Clipping(int width, int height) {
    	this.width = width;
    	this.height = height;
    	pointlist = new Vector<Point>();
    	clipBoundary = new Vector<Point>();
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.white);
		Keylistener keylistener = new Keylistener();
		Linelistener listener = new Linelistener();
		addMouseListener(listener);
		addMouseMotionListener(listener);
		addKeyListener(keylistener);		
		System.out.println("Pressing C can change to the Boundary Line");
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);

    }
    
    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawLine(Color c, int x1, int y1, int x2, int y2) {
    	int color = c.getRGB();
    	if(x1 > x2) {
    		int temp;
    		temp = x1; x1 = x2; x2 = temp;
    		temp = y1; y1 = y2; y2 = temp;
    	}
        int dx = x2 - x1;
        int dy = y2 - y1;
        int dxm = -dx;
        int dym = -dy;
        int d = 2*dy - dx;
        int d1 = 2*dx - dy;
        int dm = 2*dym-dx;
        int d1m = 2*dx - dym;
        int dE = 2*dy;
        int dEm = 2*dym;
        int dE1 = 2*dx;
        int dNE = 2*(dy - dx);
        int dNEm = 2*(dym - dx);
        int dNE1 = 2*(dx - dy);
        int dNE1m = 2*(dx - dym);
        int xf = x1, yf = y1;
        int xb = x2, yb = y2;
        canvas.setRGB(xf, yf, color);
        canvas.setRGB(xb, yb, color);

        if(Math.abs(dx) >= Math.abs(dy)){
            while(xf < xb){
                if(dy > 0){
                    ++xf; --xb;
                    if ( d < 0 )
                        d += dE;
                    else
                    {
                        d += dNE;
                        if(yf < yb){
                            ++yf;--yb;
                        }
                        else{
                            --yf;++yb;
                        }
                    }
                }
                else{
                    ++xf; --xb;
                    if ( dm < 0 )
                        dm += dEm;
                    else
                    {
                        dm += dNEm;
                        if(yf < yb){
                            ++yf;--yb;
                        }
                        else{
                            --yf;++yb;
                        }
                    }
                }
                canvas.setRGB(xf, yf, color);
                canvas.setRGB(xb, yb, color);
            }
        }
        else{
            if(dy > 0){
                if(yf <= yb){
                    while(yf < yb){
                        ++yf; --yb;
                        if(d1 < 0)
                            d1 += dE1;
                        else{
                            d1 += dNE1;
                            ++xf;
                            --xb;
                        }
                        canvas.setRGB(xf, yf, color);
                        canvas.setRGB(xb, yb, color);
                    }
                }
            }
            else{
                if(yf >= yb){
                    while(yf > yb){
                        --yf; ++yb;
                        if(d1m < 0)
                            d1m += dE1;
                        else{
                            d1m += dNE1m;
                            ++xf;
                            --xb;
                        }
                        canvas.setRGB(xf, yf, color);
                        canvas.setRGB(xb, yb, color);
                    }
                }
            }
        }
        if(xf == xb){
            while(Math.abs(yf - yb) > 1){
                if(dy > 0){
                	canvas.setRGB(xf, ++yf, color);
                }
                else {
                	canvas.setRGB(xf, ++yb, color);
                }
            }
        }
        repaint();
    }

	private class Linelistener extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			if(!isBoundaryLine) {
				pointlist.add(e.getPoint());
				if(pointlist.size() > 1) drawLine(Color.black, pointlist.get(pointlist.size()-2).x, 
						pointlist.get(pointlist.size()-2).y, pointlist.lastElement().x, pointlist.lastElement().y);
				
			}
			else {
				clipBoundary.add(e.getPoint());
				if(clipBoundary.size() > 1) drawLine(Color.red, clipBoundary.elementAt(clipBoundary.size()-2).x, 
						clipBoundary.elementAt(clipBoundary.size()-2).y, clipBoundary.lastElement().x, 
						clipBoundary.lastElement().y);
			}
		}
	}
	
	private class Keylistener extends KeyAdapter{
		private int currentP = 0;
		private int currentC = 0;
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_C:
				isBoundaryLine = !isBoundaryLine;
				System.out.println("Line's property changed!");	
				break;
			case KeyEvent.VK_I:
				if(pointlist.size() > 2) {
					if(!isBoundaryLine) {
						drawLine(Color.black, pointlist.elementAt(currentP).x, 
								pointlist.elementAt(currentP).y, 
								pointlist.lastElement().x, pointlist.lastElement().y);
						currentP = pointlist.size()-1;
					}
				}
				if(clipBoundary.size() > 2) {
					if(isBoundaryLine) {
						drawLine(Color.red, clipBoundary.elementAt(currentC).x, 
								clipBoundary.elementAt(currentC).y, 
								clipBoundary.lastElement().x, clipBoundary.lastElement().y);
						currentC = clipBoundary.size()-1;
					}
				}
				break;
			case KeyEvent.VK_O:
				canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				clipedDraw(SutherlandHodgman(new Vector<Point>(), new Vector<Point>()));
				break;
			case KeyEvent.VK_R:
				canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				pointlist.clear();
				clipBoundary.clear();
				currentP = 0;
				currentC = 0;
				repaint();
				break;
			case KeyEvent.VK_F:
				fillPoly(new Filling(pointlist));
			}
		}
	}
	
	public boolean isInside(Point point, Vector<Point> clipBoundary) {
		GeneralPath path = new GeneralPath();
		path.moveTo(clipBoundary.firstElement().getX(), clipBoundary.firstElement().getY());
		for(Point p : clipBoundary) 
			path.lineTo(p.getX(), p.getY());
		path.lineTo(clipBoundary.firstElement().getX(), clipBoundary.firstElement().getY());
		path.closePath();
		return path.contains(new Point2D.Double(point.getX(), point.getY()));
		}
	
	public Point intersect(Point a, Point b, Vector<Point> clipBoundary) {
		Point intersection = new Point();
		for(int i=0; i < clipBoundary.size(); i++) {
			Point c = clipBoundary.get(i);
			Point d = new Point();
			if(i == clipBoundary.size()-1)
				d = clipBoundary.firstElement();
			else 
				d = clipBoundary.get(i+1);

		    if (Math.abs(b.y - a.y) + Math.abs(b.x - a.x) + Math.abs(d.y - c.y)  
	            + Math.abs(d.x - c.x) == 0) {  
		        if ((c.x - a.x) + (c.y - a.y) == 0) {  
		            System.out.println("ABCD是同一个点！");  
		        } else {  
		            System.out.println("AB是一个点，CD是一个点，且AC不同！");  
		        }  
		        continue;
		    }  
		    if (Math.abs(b.y - a.y) + Math.abs(b.x - a.x) == 0) {  
		    	if ((a.x - d.x) * (c.y - d.y) - (a.y - d.y) * (c.x - d.x) == 0) {  
		    		System.out.println("A、B是一个点，且在CD线段上！");  
		    	} else {  
		    		System.out.println("A、B是一个点，且不在CD线段上！");  
		    	}  
		    	continue; 
		    }  
		    if (Math.abs(d.y - c.y) + Math.abs(d.x - c.x) == 0) {  
		    	if ((d.x - b.x) * (a.y - b.y) - (d.y - b.y) * (a.x - b.x) == 0) {  
		    		System.out.println("C、D是一个点，且在AB线段上！");  
		    	} else {  	
		    		System.out.println("C、D是一个点，且不在AB线段上！");  
		    	}  
		    	continue;  
		    }  
		    if ((b.y - a.y) * (c.x - d.x) - (b.x - a.x) * (c.y - d.y) == 0) {  
		        System.out.println("线段平行，无交点！");  
		        continue; 
		    }  
		    intersection.x = ((b.x - a.x) * (c.x - d.x) * (c.y - a.y) -   
		            c.x * (b.x - a.x) * (c.y - d.y) + a.x * (b.y - a.y) * (c.x - d.x)) /   
		            ((b.y - a.y) * (c.x - d.x) - (b.x - a.x) * (c.y - d.y));  
		    intersection.y = ((b.y - a.y) * (c.y - d.y) * (c.x - a.x) - c.y  
		            * (b.y - a.y) * (c.x - d.x) + a.y * (b.x - a.x) * (c.y - d.y))  
		            / ((b.x - a.x) * (c.y - d.y) - (b.y - a.y) * (c.x - d.x));  
		    if ((intersection.x - a.x) * (intersection.x - b.x) <= 0  
		            && (intersection.x - c.x) * (intersection.x - d.x) <= 0  
		            && (intersection.y - a.y) * (intersection.y - b.y) <= 0  
		            && (intersection.y - c.y) * (intersection.y - d.y) <= 0) {       
		        System.out.println("线段相交于点(" + intersection.x + "," + intersection.y + ")！");  
		        return intersection;
		    } else {  
		        System.out.println("线段相交于虚交点(" + intersection.x + "," + intersection.y + ")！");  
		        continue; // '相交但不在线段上  	
			}
		}
		return null;
	}
	
	public Vector<Point> SutherlandHodgman(Vector<Point> inPoly, Vector<Point> clipBoundary)
	{
		inPoly = this.pointlist;
		clipBoundary = this.clipBoundary;
		Vector<Point> outPoly = new Vector<Point>();
		Point s, p, i;
		s = inPoly.lastElement(); //start with the last point
		for (int j = 0; j < inPoly.size(); ++j) {
			p = inPoly.elementAt(j);
			if (isInside(p, clipBoundary)) {
				if (isInside(s, clipBoundary))
					outPoly.add(p); //Case1
				else {
					i = intersect(s, p, clipBoundary);
					outPoly.add(i); //Case 4
					outPoly.add(p);
				}
			}
			else if (isInside(s, clipBoundary)) {
				i = intersect(s, p, clipBoundary);
				outPoly.add(i); //Case 2
			}
			s = p;
		}
		return outPoly;
	}
	
	public void clipedDraw(Vector<Point> outPoly) {
		Point s = outPoly.lastElement();
		for(Point p : outPoly) {
			if(!pointlist.contains(p) && !pointlist.contains(s)) {}
			else
				drawLine(Color.blue, s.x, s.y, p.x, p.y);
			s = p;
		}
	}
	
	public void fillPoly(Filling f) {
		int y = f.yMin;
		int maxY = f.yMax;
		System.out.println("Ymin is: " + y+"Ymax is: " + maxY);
		Filling.Edge old, pre;
		Filling.Edge begin, end;
		while(!f.buckets.isEmpty() || f.activeedgetable!=null){
			f.etToAet(y);
			f.printAET();
			old = f.activeedgetable;
			while(true) {
				if(old.next==null) {
					break;
				}
				old = old.next;
			}
			end = old;
			pre = f.activeedgetable;
			old = pre.next;
			while(true) {
				for(int i = pre.xMin; i < old.xMin; i++) {
					canvas.setRGB(i, y, Color.green.getRGB());
				}
				if(old.next!=null) {
					pre = old.next;
					old = pre.next;
				}
				else break;
			}
			++y;
			System.out.println(y);
			while(f.getIndexByValue(f.activeedgetable, y) != -1) {
				System.out.println("Index is: "+f.getIndexByValue(f.activeedgetable, y));
				f.activeedgetable = f.dlByIndex(f.activeedgetable, f.getIndexByValue(f.activeedgetable, y));
			}
			f.plusEach();
			repaint();
		}
	}
}
