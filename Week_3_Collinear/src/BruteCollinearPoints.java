import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.text.Segment;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private final Point[] allpoints;
	private LineSegment[] lines;
	private int numLine;
	public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
	{
		numLine = 0;
		if (points == null)
		{
			throw new java.lang.NullPointerException("Input is null");
		}
		
		for (int i=0; i<points.length; i++)
		{
			if (points[i] == null)
			{
				throw new java.lang.NullPointerException("Input contains null");
			}
		}
		
		allpoints = points.clone();
		lines = new LineSegment[1];
		
		Arrays.sort(allpoints);
		for (int i=0; i<allpoints.length-1; i++)
		{
			if (allpoints[i].compareTo(allpoints[i+1]) == 0)
			{
				throw new java.lang.IllegalArgumentException("Contain duplicated points");
			}
		}
		
		if (allpoints.length >= 4)
		{
			
			for (int i=0; i<allpoints.length; i++)
				for (int j=i+1; j<allpoints.length; j++)
					for (int k=j+1; k < allpoints.length; k++)
						for (int l=k+1; l<allpoints.length; l++)
						{
							//System.out.println(points[i] + " " + points[j]);
							double slope1 = allpoints[i].slopeTo(allpoints[j]);
							double slope2 = allpoints[i].slopeTo(allpoints[k]);
							double slope3 = allpoints[i].slopeTo(allpoints[l]);
							if (slope1 == slope2 && slope2 == slope3)
							{
								
								lines[numLine++] = new LineSegment(allpoints[i], allpoints[l]);
							}
							
							if (numLine >= lines.length)
							{
								LineSegment[] temp = new LineSegment[lines.length*2];
								for (int m=0; m<numLine; m++)
								{
									temp[m] = lines[m];
								}
								lines = temp;
							}
						}
		}
		
		
		
		
	}
	public int numberOfSegments()        // the number of line segments
	{
		if (lines == null)
		{
			return 0;
		}
		return numLine;
		
	}
	public LineSegment[] segments()                // the line segments
	{
		LineSegment[] temp = new LineSegment[numLine];
		for (int i=0; i<numLine; i++)
		{
			temp[i] = lines[i];
		}
		return temp;
		
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	        //System.out.println("check:" + points[i]);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    System.out.println(collinear.segments().length);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}

}
