import java.awt.Point;
import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	private TreeSet<Point2D> point_set = new TreeSet<Point2D>();
	
	public PointSET()                               // construct an empty set of points 
	{
		
	}
	public boolean isEmpty()                      // is the set empty?
	{
		return point_set.size() == 0;
		
	}
	
	public int size()                         // number of points in the set 
	{
		return point_set.size();
		
	}
	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		if (p == null) throw new NullPointerException("argument null");
		if (!contains(p)) point_set.add(p);
	}
	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		return point_set.contains(p);
		
	}
	public void draw()                         // draw all points to standard draw 
	{
		for (Point2D p: point_set)
		{
			p.draw();
		}
	}
	public Iterable<Point2D> range(RectHV rect)	// all points that are inside the rectangle 
	{
		if (rect == null) throw new NullPointerException("argument null");
		Queue<Point2D> range_queue = new Queue<Point2D>();
		for (Point2D point : point_set)
		{
			if (rect.contains(point)) range_queue.enqueue(point);
		}
		return range_queue;
		
	}

	
	public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty 
	{
		if (p == null) throw new NullPointerException("argument null");
		if (size() == 0) return null;
		double min = Double.MAX_VALUE;
		Point2D min_point = null;
		for (Point2D point : point_set)
		{
			double distance = point.distanceTo(p);
			if (distance < min)
			{
				min = distance;
				min_point = point;
			}
		}
		return min_point;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
