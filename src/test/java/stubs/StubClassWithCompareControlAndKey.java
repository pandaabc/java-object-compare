package stubs;

import java.util.List;

import compare.annotation.JocCompareController;

public class StubClassWithCompareControlAndKey {
	List<StubClass2> stubs;
	
	@JocCompareController(crossCompare=true, indexDiff=true, key="long1")
	public List<StubClass2> getStubs() {
		return stubs;
	}

	public void setStubs(List<StubClass2> stubs) {
		this.stubs = stubs;
	}
}
