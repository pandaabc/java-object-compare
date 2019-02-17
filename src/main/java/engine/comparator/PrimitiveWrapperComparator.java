package engine.comparator;

import interfaces.ICompare;

public class PrimitiveWrapperComparator implements ICompare {

	@Override
	public boolean compare(Object newObj, Object baseObj) {
		return newObj.equals(baseObj);
	}

}
