package engine.comparator;

import java.lang.annotation.Annotation;

import compare.JocNode;
import constant.Status;

public class SimpleObjectComparator extends AbstractComparator {

	@Override
	public boolean compare(Object newObj, Object baseObj, JocNode parentNode, String path, Annotation... annotations) {
		
		JocNode curNode = constructJocNode(newObj, baseObj, parentNode, path, annotations);
		
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
