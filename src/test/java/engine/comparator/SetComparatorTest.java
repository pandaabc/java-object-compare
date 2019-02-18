package engine.comparator;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import compare.JocNode;
import constant.Status;
import stubs.TestStubs;

public class SetComparatorTest {
	
	@Test
	public void testCompare() {
		Set<String> a = new HashSet<>();
		Set<String> b = new HashSet<>();
		a.add("t1");
		a.add("t3");
		b.add("t1");
		b.add("t2");
		
		SetComparator comparator = new SetComparator();
		
		JocNode node = TestStubs.getJocNode();
		
		Assert.assertFalse(comparator.compare(a, b, node, ""));
		
		JocNode child = node.getChildren().get(0);
		
		Assert.assertEquals(3, child.getChildren().size());
		Assert.assertTrue(child.getChildren().get(0).getStatus().equals(Status.NOCHANGE) || child.getChildren().get(1).getStatus().equals(Status.NOCHANGE) || child.getChildren().get(2).getStatus().equals(Status.NOCHANGE));
		Assert.assertTrue(child.getChildren().get(0).getStatus().equals(Status.UNEXPECTED) || child.getChildren().get(1).getStatus().equals(Status.UNEXPECTED) || child.getChildren().get(2).getStatus().equals(Status.UNEXPECTED));
		Assert.assertTrue(child.getChildren().get(0).getStatus().equals(Status.MISSING) || child.getChildren().get(1).getStatus().equals(Status.MISSING) || child.getChildren().get(2).getStatus().equals(Status.MISSING));
	}

}
