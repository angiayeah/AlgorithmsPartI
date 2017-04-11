import java.util.Iterator;

import javax.activation.MailcapCommandMap;
import javax.naming.InitialContext;
import javax.naming.TimeLimitExceededException;
import javax.xml.bind.SchemaOutputResolver;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Boolean solvable = true;
	private int move = 0;
	private Stack<Board> sols = new Stack<Board>();
	
	public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
	{
		if (initial == null)
		{
			throw new java.lang.NullPointerException("Null object");
		}
		
		MinPQ<Steps> pq = new MinPQ<Steps>();
		MinPQ<Steps> twin_pq = new MinPQ<Steps>();
		Board twin_initial = initial.twin();
		Board next = null;
		Board twin_next = null;
		Steps current = null;
		Steps twin_current = null;
		pq.insert(new Steps(initial,  null, 0));
		twin_pq.insert(new Steps(twin_initial,  null, 0));
		//StdOut.println(twin_initial.toString());
		while (true)
		{
//			StdOut.println(times);
			current = pq.delMin();
			next = current.board;
			if (next.isGoal())
			{
				move = current.move;
				break;
			}
//			StdOut.println("Selected: ");
//			StdOut.println(next.toString());
			//for twin board to run
			twin_current = twin_pq.delMin();
			twin_next = twin_current.board;
			if (twin_next.isGoal())
			{
				solvable = false;
				break;
			}
			for (Board neighbor : next.neighbors())
			{
				if (current.prev == null || !neighbor.equals(current.prev.board)) 
				{
					pq.insert(new Steps(neighbor,current, current.move+1));
				}
				
			}
			for (Board neighbor : twin_next.neighbors())
			{
				if (twin_current.prev == null || !neighbor.equals(twin_current.prev.board)) twin_pq.insert(new Steps(neighbor,twin_current, twin_current.move+1));
			}
//			StdOut.println(times + " time queue: " + pq.size());
//			for (Steps s : pq)
//			{
//				StdOut.println(s.board.toString() + "  " + (s.board.manhattan()+s.move));
//			}
		}
		
		while (current != null)
		{
			sols.push(current.board);
			current = current.prev;
		}
		
		
		//System.out.println(pq.delMin().board.toString());
	}
	
	private class Steps implements Comparable<Steps>
	{
		Board board;
		Steps prev;
		int move;
		public Steps(Board board, Steps prev, int size) {
			this.board = board;
			this.prev = prev;
			move = size;
		}
		@Override
		public int compareTo(Steps o) {
			return (this.board.manhattan() - (o.board.manhattan()) + (move - (o.move)));
		}
		
	}
	
	public boolean isSolvable()            // is the initial board solvable?
	{
		return solvable;
		
	}
	public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
	{
		if (!isSolvable()) return -1;
		return move;
		
	}
	public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
	{
		if (!isSolvable()) return null;
		
		
		return sols;
		
	}
	
	
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);
	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	        {
	        	StdOut.println(board);
	        }
	    }
	}
	       
}
