package engine.comparator;

import java.lang.annotation.Annotation;
import java.util.Set;

import compare.JocNode;
import compare.utils.JocNodeUtils;
import constant.Status;
import interfaces.ICompare;

public class SetComparator extends AbstractComparator {

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
			boolean res1 = compareOneWay((Set<Object>) newObj, (Set<Object>) baseObj, curNode);
			boolean res2 = compareOtherWay((Set<Object>) newObj, (Set<Object>) baseObj, curNode);
			
			if (!res1 || !res2) {
				noChange = false;
			}
			
		}
		
		curNode.setStatus(noChange ? Status.NOCHANGE : Status.CHANGE);
		
		return noChange;
	}
	
	protected boolean compareOneWay(Set<Object> newObj, Set<Object> baseObj, JocNode parentNode) {
		
		boolean noChange = true;
		
		for (Object o : newObj) {
			
			if (baseObj.contains(o)) {
				JocNodeUtils.createNoChangeJocNode(o, parentNode);
			} else {
				JocNodeUtils.createMissingJocNode(o, parentNode);
				noChange = false;
			}
			
		}
		
		return noChange;
		
	}


	public static boolean compareOtherWay(Set<Object> newObj, Set<Object> baseObj, JocNode parentNode) {
		
		boolean noChange = true;
		
		for (Object o : baseObj) {
			
			if (!newObj.contains(o)) {
				JocNodeUtils.createUnexpectedJocNode(o, parentNode);
				noChange = false;
			} 
			
		}
		
		return noChange;
		
	}

}
