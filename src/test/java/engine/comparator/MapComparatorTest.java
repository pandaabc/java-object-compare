package engine.comparator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import compare.JocNode;
import constant.Status;
import engine.CompareFactory;
import stubs.TestStubs;

public class MapComparatorTest {
	
	@Test
	public void testCompare() {
		Map<String, String> a = new HashMap<>();
		Map<String, String> b = new HashMap<>();
		a.put("t1","tr1");
		a.put("t3","tr3");
		a.put("t4","tr4");
		b.put("t1","tr1");
		b.put("t2","tr2");
		b.put("t4","tr5");
		
		MapComparator comparator = new MapComparator(new CompareFactory());
		
		JocNode node = TestStubs.getJocNode();
		
		Assert.assertFalse(comparator.compare(a, b, node, ""));
		
		JocNode child = node.getChildren().get(0);
		
		Assert.assertEquals(4, child.getChildren().size());
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.NOCHANGE)));
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.UNEXPECTED)));
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.MISSING)));
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.CHANGE)));
	}

}
