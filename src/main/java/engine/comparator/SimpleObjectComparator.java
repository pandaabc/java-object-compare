package engine.comparator;

import java.lang.annotation.Annotation;

import compare.JocNode;
import constant.Status;
import interfaces.ICompare;

public class SimpleObjectComparator implements ICompare {

	@Override
	public boolean compare(Object newObj, Object baseObj, JocNode parentNode, String path, Annotation... annotations) {
		
		JocNode curNode = new JocNode(newObj, baseObj, path);
		curNode.setParent(parentNode);
		parentNode.addChild(curNode);
		
		boolean noChange = true;
		
		if (newObj == null && baseObj != null
				|| newObj != null && baseObj == null) {
			noChange = false;
		} else if (newObj == null && baseObj == null) {
			noChange = true;
		} else {
			noChange = newObj.equals(baseObj);
		}
		
		curNode.setStatus(noChange ? Status.NOCHANGE : Status.CHANGE);
		
		return noChange;
	}

}
