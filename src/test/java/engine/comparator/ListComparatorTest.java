package engine.comparator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import compare.JocNode;
import constant.Status;
import engine.CompareFactory;
import stubs.StubClass2;
import stubs.StubClassWithCompareControl;
import stubs.StubClassWithCompareControlAndKey;
import stubs.TestStubs;

public class ListComparatorTest {
	
	@Test
	public void testCompareOrderredCompare() {
		List<StubClass2> l1 = new ArrayList<>();
		List<StubClass2> l2 = new ArrayList<>();
		
		StubClass2 a1 = new StubClass2();
		a1.setLong1(10001l);
		a1.setDouble1(0.001);
		a1.setTest1("Test");
		
		StubClass2 a2 = new StubClass2();
		a2.setLong1(10002l);
		a2.setDouble1(0.001);
		a2.setTest1("Test");
		
		StubClass2 a3 = new StubClass2();
		a3.setLong1(10001l);
		a3.setDouble1(0.001);
		a3.setTest1("Test");
		
		StubClass2 a4 = new StubClass2();
		a4.setLong1(10002l);
		a4.setDouble1(0.01);
		a4.setTest1("Test1");
		
		StubClass2 a5 = new StubClass2();
		a5.setLong1(10003l);
		a5.setDouble1(1.);
		a5.setTest1("Test33");
		
		l1.add(a1);
		l1.add(a2);
		
		l2.add(a3);
		l2.add(a4);
		l2.add(a5);
		
		ListComparator comparator = new ListComparator(new CompareFactory());
		
		JocNode node = TestStubs.getJocNode();
		
		Assert.assertFalse(comparator.compare(l1, l2, node, ""));
		
		JocNode child = node.getChildren().get(0);
		
		Assert.assertEquals(3, child.getChildren().size());
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.NOCHANGE)));
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.UNEXPECTED)));
		Assert.assertTrue(child.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.CHANGE)));
	}
	
	@Test
	public void testCompareCrossCompare() {
		List<StubClass2> l1 = new ArrayList<>();
		List<StubClass2> l2 = new ArrayList<>();
		
		StubClass2 a1 = new StubClass2();
		a1.setLong1(10001l);
		a1.setDouble1(0.001);
		a1.setTest1("Test");
		
		StubClass2 a2 = new StubClass2();
		a2.setLong1(10002l);
		a2.setDouble1(0.001);
		a2.setTest1("Test");
		
		StubClass2 a3 = new StubClass2();
		a3.setLong1(10001l);
		a3.setDouble1(0.001);
		a3.setTest1("Test");
		
		StubClass2 a4 = new StubClass2();
		a4.setLong1(10002l);
		a4.setDouble1(0.01);
		a4.setTest1("Test1");
		
		StubClass2 a5 = new StubClass2();
		a5.setLong1(10003l);
		a5.setDouble1(1.);
		a5.setTest1("Test33");
		
		l1.add(a1);
		l1.add(a2);
		
		l2.add(a3);
		l2.add(a4);
		l2.add(a5);
		
		StubClassWithCompareControl c1 = new StubClassWithCompareControl();
		c1.setStubs(l1);
		
		StubClassWithCompareControl c2 = new StubClassWithCompareControl();
		c2.setStubs(l2);
				
		JocNode node = TestStubs.getJocNode();
		
		Assert.assertFalse(new CompareFactory().getComparator(c1, c2).compare(c1, c2, node, ""));
		
		JocNode child = node.getChildren().get(0);
		
		Assert.assertEquals(1, child.getChildren().size());
		
		JocNode grandChild = node.getChildren().get(0).getChildren().get(0);
		
		Assert.assertEquals(5, grandChild.getChildren().size());
		Assert.assertTrue(grandChild.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.UNEXPECTED)));
		Assert.assertTrue(grandChild.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.MISSING)));
	}
	
	@Test
	public void testCompareCrossCompareWithKey() {
		List<StubClass2> l1 = new ArrayList<>();
		List<StubClass2> l2 = new ArrayList<>();
		
		StubClass2 a1 = new StubClass2();
		a1.setLong1(10001l);
		a1.setDouble1(0.001);
		a1.setTest1("Test");
		
		StubClass2 a2 = new StubClass2();
		a2.setLong1(10002l);
		a2.setDouble1(0.001);
		a2.setTest1("Test");
		
		StubClass2 a3 = new StubClass2();
		a3.setLong1(10001l);
		a3.setDouble1(0.001);
		a3.setTest1("Test");
		
		StubClass2 a4 = new StubClass2();
		a4.setLong1(10002l);
		a4.setDouble1(0.01);
		a4.setTest1("Test1");
		
		StubClass2 a5 = new StubClass2();
		a5.setLong1(10003l);
		a5.setDouble1(1.);
		a5.setTest1("Test33");
		
		l1.add(a1);
		l1.add(a2);
		
		l2.add(a3);
		l2.add(a4);
		l2.add(a5);
		
		StubClassWithCompareControlAndKey c1 = new StubClassWithCompareControlAndKey();
		c1.setStubs(l1);
		
		StubClassWithCompareControlAndKey c2 = new StubClassWithCompareControlAndKey();
		c2.setStubs(l2);
				
		JocNode node = TestStubs.getJocNode();
		
		Assert.assertFalse(new CompareFactory().getComparator(c1, c2).compare(c1, c2, node, ""));
		
		JocNode child = node.getChildren().get(0);
		
		Assert.assertEquals(1, child.getChildren().size());
		
		JocNode grandChild = node.getChildren().get(0).getChildren().get(0);
		
		Assert.assertEquals(3, grandChild.getChildren().size());
		Assert.assertTrue(grandChild.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.UNEXPECTED)));
		Assert.assertTrue(grandChild.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.NOCHANGE)));
		Assert.assertTrue(grandChild.getChildren().stream().anyMatch(e -> e.getStatus().equals(Status.CHANGE)));
	}

}
