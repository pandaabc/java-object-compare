package engine.comparator;

import org.junit.Assert;
import org.junit.Test;

public class PrimitiveWrapperComparatorTest {
	
	@Test
	public void testCalculator() {
		
		PrimitiveWrapperComparator comparator = new PrimitiveWrapperComparator();
		Assert.assertTrue(comparator.compare(new Double(1.0), new Double(1.0)));
		Assert.assertFalse(comparator.compare(new Double(1.0), null));
		Assert.assertFalse(comparator.compare(new Double(1.0), new Double(2.0)));
		Assert.assertFalse(comparator.compare(new Double(1.0), new Integer(1)));
		Assert.assertTrue(comparator.compare(new Integer(1), new Integer(1)));
		
	}

}
