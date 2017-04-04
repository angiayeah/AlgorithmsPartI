import org.junit.Test.*;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;

public class TestRandom {
	
	RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
	
	@Test
	public void testIterator()
	{
		for (int i=0; i<10; i++)
		{
			rq.enqueue(i);
		}
		
		//two iterator test
		Iterator iterator1 = rq.iterator();
		Iterator iterator2 = rq.iterator();
		while(iterator1.hasNext())
		{
			System.out.print(iterator1.next()+" ");
		}
		System.out.println("Next: ");
		
		while(iterator2.hasNext())
		{
			System.out.print(iterator2.next()+" ");
		}
		System.out.println("Done");
		
		for (int each : rq)
		{
			System.out.print(each+" ");
		}
		System.out.println();
	}
}
