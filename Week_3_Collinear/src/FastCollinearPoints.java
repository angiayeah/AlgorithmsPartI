import java.util.Arrays;

import javax.tools.JavaCompiler;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private Point[] allpoints;
	private LineSegment[] lines;
	private int size = 0;
	public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
	{
		//corner case
		if (points == null)
		{
			throw new java.lang.NullPointerException("Input is null");
		}
		
		allpoints = new Point[points.length];
		lines = new LineSegment[1];
		
		for (int i=0; i<points.length; i++)
		{
			if (points[i] == null)
			{
				throw new java.lang.NullPointerException("Contains null");
			}
			allpoints[i] = points[i];
		}
		//sort array first
		Arrays.sort(allpoints);
		
//		for (int i=0; i<allpoints.length; i++)
//		{
//			System.out.print(allpoints[i] + " ");
//		}
//		System.out.println();
	
		
		for (int i=0; i<allpoints.length; i++)
		{
			//System.out.println(size);
			if (i<allpoints.length-1)
			{
				if (allpoints[i].compareTo(allpoints[i+1]) == 0) throw new java.lang.IllegalArgumentException("Duplicated points");
			}
			
			Point[] slopePoints = allpoints.clone();
			
			//sort by slope for a specific point
//			System.out.println("For this point: " + allpoints[i]);
			//sort points in two parts, first parts are checked points, second parts are new points
			if (i > 1 ) Arrays.sort(slopePoints, 0, i, allpoints[i].slopeOrder());
			if (i < slopePoints.length-1) Arrays.sort(slopePoints, i+1, slopePoints.length, allpoints[i].slopeOrder());
			
			
//			for (int j=0; j<slopePoints.length; j++)
//			{
//				System.out.print(slopePoints[j] + " ");
//			}
//			System.out.println();
//			System.out.println("i: " + i + " " +allpoints[i]);
			int start = i+1;
			int left_start = 0;
			while (start < slopePoints.length)
			{
				
				
//				System.out.println("Resort:");
//				for (int j=0; j<slopePoints.length; j++)
//				{
//					System.out.print(slopePoints[j] + " ");
//				}
//				System.out.println();
				
				int end = start;
				//System.out.println(start + " " + slopePoints[start] + " " + allpoints[i].slopeTo(slopePoints[start]) );
				//if end is not out of range, if the two adjecents' slopes are equal
				while(end < slopePoints.length-1 
						&& allpoints[i].slopeTo(slopePoints[end]) == allpoints[i].slopeTo(slopePoints[end+1]))
				{
//					System.out.println("adjecents found! " + start +" "+ (end+1));
//					System.out.println(slopePoints[end] + " " + slopePoints[end+1]);
					end ++;
				}
				if (end-start >= 2)
				{
//					Point[] segments = new Point[end-start +2];
//					segments[0] = slopePoints[0];
//					for (int j=start, k=1; j<= end; j++, k++)
//					{
//						segments[k] = slopePoints[j]; 
//					}
//					Arrays.sort(segments);		//sort to make the line as long as possible
//					System.out.println("segment found!");
//					System.out.println(segments[0] + " " + segments[segments.length-1]);
//					System.out.println(slopePoints.length +" " + end + " " + size);
					
					
					//efficient way to check subsegment, compare the slope of points behind with that of points in the front
					boolean isSub = false;
					for (; left_start<i; left_start++)
					{
						//System.out.println(left_start);
						if (allpoints[i].slopeTo(slopePoints[left_start]) == allpoints[i].slopeTo(slopePoints[end]))
						{
							isSub = true;
							break;
						}
						if (allpoints[i].slopeTo(slopePoints[left_start]) > allpoints[i].slopeTo(slopePoints[end])) break;
					}
					
					if (!isSub) lines[size++] = new LineSegment(allpoints[i], slopePoints[end]);
				}
				start = end+1;
				
				if (size >= lines.length)
				{
					LineSegment[] temp = new LineSegment[lines.length * 2];
					for (int j=0; j<size; j++)
					{
						temp[j] = lines[j];
					}
					lines = temp;
				}
			}
			
			
		}
		
	}
	public int numberOfSegments()        // the number of line segments
	{
		return size;
	}
	public LineSegment[] segments()                // the line segments
	{
		LineSegment[] temp = new LineSegment[size];
		for (int i=0; i<size; i++)
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
	        points[i].draw();
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
