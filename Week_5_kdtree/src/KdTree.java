import java.awt.Point;
import java.awt.font.NumericShaper.Range;

import org.w3c.dom.css.Rect;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private Node root;	//root
	public KdTree()		// construct an empty set of points 
	{
		root = null;
	}
	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node left;        // the left/bottom subtree
		private Node right;        // the right/top subtree
		private int count;
		
		public Node (Point2D p)
		{
			this.p = p;
		}
		
	}

	public boolean isEmpty()                      // is the set empty?
	{
		return size(root) == 0;
		
	}
	
	public int size()                         // number of points in the set 
	{
		return size(root);
		
	}
	private int size(Node node)
	{
		if (node == null) return 0;
		return node.count;
	}
	public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
	{
		root = insert(root, p, true);	//root starts with coordinate x compare
	}
	private Node insert(Node node, Point2D p, Boolean isX)
	{
		double x = p.x();
		double y = p.y();
		//add new Node
		if (node == null)
		{
			Node temp = new Node(p);
			temp.rect = new RectHV(p.x(), p.y(), p.x(), p.y());
			//if (size() == 0) temp.rect = new RectHV(0, 0, 1, 1); //starts with the biggest range for root.
			temp.count = 1;
			return temp;
		}
		if (node.p.x() == x && node.p.y() == y) return node;
		
		//update rect
		if (!node.rect.contains(p))
		{

			double xmin = node.rect.xmin();
			double xmax = node.rect.xmax();
			double ymin = node.rect.ymin();
			double ymax = node.rect.ymax();
			if (x < xmin) xmin = x;
			else if (x > xmax) xmax = x;
			if (y < ymin) ymin = y;
			else if (y > ymax) ymax = y;
			node.rect = new RectHV(xmin, ymin, xmax, ymax);

		}

		
		
		//iterate according to value
		if (isX)
		{
			if (p.x() < node.p.x())
			{
				node.left = insert(node.left, p, !isX);
				//node.left.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
			}
			else 
			{
				node.right = insert(node.right, p, !isX);
				//node.right.rect = new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());			
			}
			
		}
		else
		{
			if (p.y() < node.p.y())
			{
				node.left = insert(node.left, p, !isX);
				//node.left.rect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
			}
			else
			{
				node.right = insert(node.right, p, !isX);
				//node.right.rect = new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
				
			}
		}
		
		
		//update count number
		node.count = size(node.left) + size(node.right) + 1;
		
		return node;
	}
	public boolean contains(Point2D p)            // does the set contain point p? 
	{
		return contains(root, p, true);	//starts with compare x-coordinate
		
	}
	private boolean contains(Node node, Point2D p, Boolean isX)
	{
		if (node == null) return false;
		if (node.p.equals(p)) return true;
		if (isX)
		{
			if (p.x() < node.p.x()) return contains(node.left, p, !isX);
			else return contains(node.right, p, !isX);
		}
		else 
		{
			if (p.y() < node.p.y()) return contains(node.left, p, !isX);
			else return contains(node.right, p, !isX);
		}
		
		
	}
	public void draw()                         // draw all points to standard draw 
	{
		//root.rect.draw();
		//System.out.println("size: " + root.count);
		draw(root);
	}
	private void draw(Node node)
	{
		//System.out.println("draw: " + node.p.toString());
		//node.rect.draw();
		node.p.draw();
		//System.out.println(node.rect.toString());
		if (node.left != null) draw(node.left);
		if (node.right != null) draw(node.right);
		
	}
	public Iterable<Point2D> range(RectHV rect)	// all points that are inside the rectangle 
	{
		Queue<Point2D> range_set = new Queue<Point2D>();
		range(root, range_set, rect);
		return range_set;
	}
	private void range(Node node, Queue<Point2D> range_set, RectHV that)
	{
		if (node != null)
		{
			if (that.contains(node.p)) range_set.enqueue(node.p);
			if (node.right != null && node.right.rect.intersects(that)) range(node.right, range_set, that);
			if (node.left != null && node.left.rect.intersects(that)) range(node.left, range_set, that);
		}
	}

	
	public Point2D nearest(Point2D p) // a nearest neighbor in the set to point p; null if the set is empty 
	{
		if (root == null) return null;
		return nearest(root, root.p, p, root.p.distanceTo(p), true);	//starts with x-coordinate
		
	}
	
	private Point2D nearest (Node node, Point2D min_point, Point2D p, double min, boolean isX)
	{
		double distance = node.p.distanceTo(p);
		if (distance < min)
		{
			min = distance;
			min_point = node.p;
		}
		boolean go_left = false;
		boolean go_right = false;
		boolean right_first = false;
		if (node.right != null && node.right.rect.distanceTo(p) < min) go_right = true;
		if (node.left != null && node.left.rect.distanceTo(p) < min) go_left = true;
		//choose to go to the subtree that the Point p will go
		if (go_left && go_right)
		{
			if (isX)
			{
				if (p.x() < node.p.x()) right_first = false;
				else right_first = true;
			}
			else
			{
				if (p.y() < node.p.y()) right_first = false;
				else right_first = true;
			}
		}
		if (go_left && go_right)
		{
			if (!right_first)
			{
				min_point = nearest(node.left, min_point, p, min, !isX);
				min = min_point.distanceTo(p);
				if (node.right != null && node.right.rect.distanceTo(p) < min) min_point = nearest(node.right, min_point, p, min, !isX);
			}
			else
			{
				min_point = nearest(node.right, min_point, p, min, !isX);
				min = min_point.distanceTo(p);
				if (node.left != null && node.left.rect.distanceTo(p) < min) min_point = nearest(node.left, min_point, p, min, !isX);
			}
			
		} 
		if (go_right && !go_left) min_point = nearest(node.right, min_point, p, min, !isX);
		if (go_left && !go_right) min_point = nearest(node.left, min_point, p, min, !isX);
		return min_point;
	}
	
	
	public static void main(String[] args) {
		RectHV rectHV = new RectHV(0.64462, 0.28166,0.88067, 0.40828);
		rectHV.draw();
		KdTree tree = new KdTree();
		tree.insert(new Point2D(0.79372, 0.40758));
		tree.insert(new Point2D(0.74, 0.30758));
		tree.draw();
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.setPenRadius(0.05);
		for (Point2D point2d : tree.range(rectHV))
		{
			point2d.draw();
		}
	}

}
