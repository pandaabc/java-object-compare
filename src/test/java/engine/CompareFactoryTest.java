package engine;

import org.junit.Assert;
import org.junit.Test;

import engine.comparator.DefaultComparator;
import engine.comparator.SimpleObjectComparator;

public class CompareFactoryTest {
	
	CompareFactory factory = new CompareFactory();
	
	@Test
	public void testGetComparator() {
		
		Assert.assertEquals(SimpleObjectComparator.class, factory.getComparator(1.0, 1.0).getClass());
		Assert.assertEquals(DefaultComparator.class, factory.getComparator(1.0, 1).getClass());
		
	}

}
