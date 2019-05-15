package main;

import java.awt.Point;
import java.util.Vector;

import java.util.HashMap;


public class Filling {

	public Vector<Edge> edgelist;
	public Edge edgetable = null;
	public Edge activeedgetable = null;
	public HashMap<Integer, Edge> buckets;
	public int yMin;
	public int yMax;
	
	public Filling(Vector<Point> pointlist) {
		this.buckets = new HashMap<Integer, Edge>();
		this.edgelist = new Vector<Edge>();
		Point s = pointlist.lastElement();
		for(Point p : pointlist) {
			edgelist.add(new Edge(s, p));
			s = p;
		}	
		yMin = getMinY();
		yMax = getMaxY();
		Init();
	}

	public void Init() {
		int edgesize = edgelist.size();
		Edge old = null, curEdge = edgetable;
		int curIndex = 0;
		int curYmin = 0;
		curEdge = getMinYp();
		curYmin = getMinY();
		curIndex = edgelist.indexOf(curEdge);
		edgelist.setElementAt(null, curIndex);
		edgesize--;
		old = curEdge;
		curEdge = getMinYp();
		if(curEdge == old) {
			edgelist.setElementAt(null, edgelist.indexOf(curEdge));
			edgesize--;
			if(old.xMax() < curEdge.xMax()) {
				Edge tmpE = old;
				old = curEdge;
				curEdge = tmpE;
			}
			curEdge.next = old;
			old = curEdge;
			buckets.put(curYmin, old);
		}
		else {
			old.next = buckets.get(curYmin);
			buckets.put(curYmin, old);
		}	
		while(edgesize != 0) {
			curEdge = getMinYp();
			curYmin = getMinY();
			curIndex = edgelist.indexOf(curEdge);
			edgelist.setElementAt(null, curIndex);
			edgesize--;
			old = curEdge;
			curEdge = getMinYp();
			if(curEdge == old) {
				edgelist.setElementAt(null, edgelist.indexOf(curEdge));
				edgesize--;
				if(old.xMax() < curEdge.xMax()) {
					Edge tmpE = old;
					old = curEdge;
					curEdge = tmpE;
				}
				curEdge.next = old;
				old = curEdge;
				buckets.put(curYmin, old);
			}
			else {
				old.next = buckets.get(curYmin);
				buckets.put(curYmin, old);
			}				
		}		
	}
	
	public int getMaxY() {
		int yMax = 0;
		for(Edge e : this.edgelist) {
			if(e != null)
				if(e.yMax > yMax)
					yMax = e.yMax;
		}
		return yMax;
	}
	
	public Edge getMaxYp() {
		int yMax = 0;
		Edge yM = null;
		for(Edge e : this.edgelist) {
			if(e != null)
				if(e.yMax > yMax) {
					yMax = e.yMax;
					yM = e;
				}
		}
		return yM;
	}
	
	public int getMinY() {
		int yMin = 100000;
		for(Edge e : this.edgelist) {
			if(e != null)
				if(e.getFirst().y < yMin || e.getSecond().y < yMin) 
					yMin = e.getFirst().y < e.getSecond().y ? e.getFirst().y:e.getSecond().y;			
			}
		return yMin;
	}
	
	public Edge getMinYp() {
		int yMin = 100000;
		Edge yM = null;
		for(Edge e : this.edgelist) {
			if(e != null)
				if(e.getFirst().y < yMin || e.getSecond().y < yMin) {
					yMin = e.getFirst().y < e.getSecond().y ? e.getFirst().y:e.getSecond().y;	
					yM = e;				
				}
		}
		return yM;
	}
	
	public void printET() {
		Edge e;
		for(int i=this.yMin; i <= this.yMax; i++) {
			if(!buckets.containsKey(i)) continue;
			e = buckets.get(i);
			System.out.print("Bucket "+i+": ");
			System.out.print("[(ymax:"+e.yMax+"), (xmin:"+e.xMin+"), (dx/dy:"+e.m+")");
			while(e.next != null) {
				e = e.next;
				System.out.print(" --> (ymax:"+e.yMax+"), (xmin:"+e.xMin+"), (dx/dy:"+e.m+")");
			}
			System.out.println("]");
		}
	}
	
	public void etToAet(int y) {
		Edge old;
		if(buckets.containsKey(y)) {
			if(activeedgetable == null) activeedgetable = buckets.get(y);
			else {
				old = activeedgetable;
				while(true) {
					if(old.next==null) {
						old.next = buckets.get(y); 
						break;
					}		
					old = old.next;
				}
			}
			quickSort();
		}
	}
	
	public void quickSort() {
		Vector<Edge> es = new Vector<Edge>();
		Edge e = this.activeedgetable;
		es.add(e);
		while(e.next != null) {
			e = e.next;
			es.add(e);
		}
		for(int i = 0; i < es.size()-1; i++) {
			for(int j = es.size()-1; j>i; j--) {
				if(es.get(j).xMin < es.get(j-1).xMin) {
					Edge tmp = es.get(j);
					es.setElementAt(es.get(j-1), j);
					es.setElementAt(tmp, j-1);
				}
			}
		}
		this.activeedgetable = null;
		e = this.activeedgetable;
		e = es.firstElement();
		es.remove(es.indexOf(es.firstElement()));
		while(!es.isEmpty()) {
			e.next = es.firstElement();
			es.remove(es.indexOf(es.firstElement()));
			e = e.next;
		}
    }

	public void deleteNode(Edge head ,int x){
		Edge p = head;
	    Edge q=p.next;
	    if(p.yMax == x) {
	    	head = null;
	    }
	    while(q!=null){
	    	if(q.yMax==x){
	    		p.next = q.next;
	    		q = q.next;
	    	}
	    	if(q.next == null) {
	    		if(q.yMax == x) 
	    			q = null;
	    	}
	    	else {
		    	p=q;
		    	q=q.next;
	    	}
	    }
	}

	public class Edge{
		private Point point1;
		private Point point2;
		public int yMax;
		public int xMin;
		public float m;
		public Edge next;
		
		public Edge(Point point1, Point point2) {
			this.point1 = point1;
			this.point2 = point2;
			if(this.point1.y > this.point2.y) {
				this.yMax = this.point1.y;
				this.xMin = this.point2.x;
			}
			else {
				this.yMax = this.point2.y;
				this.xMin = this.point1.x;
			}
			this.m = (this.point1.x - this.point2.x)/(this.point1.y - this.point2.y);
		}
		public Point getFirst() {
			return this.point1;
		}
		public Point getSecond() {
			return this.point2;
		}
		public int yMin() {
			return this.point1.y > this.point2.y ? this.point2.y : this.point1.y;
		}
		public int xMax() {
			return this.point1.y > this.point2.y ? this.point1.x : this.point2.x;
		}
		public boolean isInclude(Point point) {
			return point == point1 || point == point2;
		}
		public Point getYMaxPoint() {
			return point1.y > point2.y ? point1 : point2;
		}
		public Point getXMinPoint() {
			return point1.x > point2.x ? point2 : point1;
		}
		public Point getXMaxPoint() {
			return point1.x > point2.x ? point1 : point2;
		}		
	}
}
