package stubs;

import java.util.List;

import compare.annotation.JocCompareControl;

public class StubClassWithCompareControl {
	List<StubClass2> stubs;
	
	@JocCompareControl(crossCompare=true, indexDiff=true)
	public List<StubClass2> getStubs() {
		return stubs;
	}

	public void setStubs(List<StubClass2> stubs) {
		this.stubs = stubs;
	}
}
