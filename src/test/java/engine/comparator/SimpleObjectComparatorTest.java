package engine.comparator;

import org.junit.Assert;
import org.junit.Test;

import compare.JocNode;
import stubs.TestStubs;

public class SimpleObjectComparatorTest {
	
	@Test
	public void testCalculator() {
		
		JocNode stubNode = TestStubs.getJocNode();
		
		SimpleObjectComparator comparator = new SimpleObjectComparator();
		Assert.assertTrue(comparator.compare(new Double(1.0), new Double(1.0), stubNode, ""));
		Assert.assertTrue(comparator.compare(null, null, stubNode, ""));
		Assert.assertFalse(comparator.compare(new Double(1.0), null, stubNode, ""));
		Assert.assertFalse(comparator.compare(null, new Boolean(true), stubNode, ""));
		Assert.assertFalse(comparator.compare(new Double(1.0), new Double(2.0), stubNode, ""));
		Assert.assertFalse(comparator.compare(new Double(1.0), new Integer(1), stubNode, ""));
		Assert.assertTrue(comparator.compare(new Integer(1), new Integer(1), stubNode, ""));
		
	}

}
