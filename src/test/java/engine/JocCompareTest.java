package engine;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import compare.JocNode;
import constant.Status;
import engine.report.JocReport;
import stubs.StubClass2;
import stubs.StubClassWithCompareControlAndKey;
import stubs.TestStubs;

public class JocCompareTest {

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
				
		JocCompare joc = JocCompare.getBuilder().buildDefaultJocCompare(c1, c2);
		JocReport report = joc.compare().reportDiff();
		
		report.getReports().forEach(System.out::println);
		
		Assert.assertEquals(6, report.getReports().size());
				
	}
}
