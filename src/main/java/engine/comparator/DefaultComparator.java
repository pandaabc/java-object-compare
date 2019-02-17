package engine.comparator;

import interfaces.ICompare;

public class DefaultComparator implements ICompare {

	@Override
	public boolean compare(Object newObj, Object baseObj) {
		if (!newObj.getClass().equals(baseObj.getClass())){
			return false;
		}
		return false;
	}

}
