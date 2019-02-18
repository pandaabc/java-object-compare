package stubs;

import java.util.List;

import compare.annotation.JocCompareControl;

public class StubClassWithCompareControlAndKey {
	List<StubClass2> stubs;
	
	@JocCompareControl(crossCompare=true, indexDiff=true, key="long1")
	public List<StubClass2> getStubs() {
		return stubs;
	}

	public void setStubs(List<StubClass2> stubs) {
		this.stubs = stubs;
	}
}
