import org.junit.Test.*;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.security.acl.LastOwnerException;

public class TestDeque {
	Deque<Integer> deque = new Deque<Integer>();
	
	@Test
	public void testQueue()
	{
		for (int i=0; i<5; i++)
		{
			deque.addLast(i);
			System.out.println(i);
		}
		System.out.println("Start to remove");
		for (int i=0; i<5; i++)
		{
			System.out.println(deque.removeLast());
		}
		
		
		System.out.println("***************do it again***************");
		for (int i=0; i<5; i++)
		{
			deque.addLast(i);
			System.out.println(i);
		}
		for (int i=0; i<4; i++)
		{
			System.out.println(deque.removeFirst());
		}
		
		
		int last = (int) deque.removeLast();
//		System.out.println(last);
		assertEquals(last, 49);
	}
	
}
