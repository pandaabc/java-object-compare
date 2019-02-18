package engine.comparator;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Map.Entry;

import compare.JocNode;
import compare.utils.JocNodeUtils;
import constant.Status;
import engine.CompareFactory;

public class MapComparator extends AbstractComparator {

	CompareFactory factory;
	
	public MapComparator(CompareFactory factory) {
		this.factory = factory;
	}
	
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
			boolean res1 = compareOneWay((Map<Object, Object>) newObj, (Map<Object, Object>) baseObj, curNode);
			boolean res2 = compareOtherWay((Map<Object, Object>) newObj, (Map<Object, Object>) baseObj, curNode);
			
			if (!res1 || !res2) {
				noChange = false;
			}
			
		}
		
		curNode.setStatus(noChange ? Status.NOCHANGE : Status.CHANGE);
		
		return noChange;
	}

protected boolean compareOneWay(Map<Object, Object> newObj, Map<Object, Object> baseObj, JocNode parentNode) {
		
		boolean noChange = true;
		
		for (Entry<Object, Object> o : newObj.entrySet()) {
			
			if (baseObj.containsKey(o.getKey())) {
				
				boolean curRes = factory.getComparator(o.getValue(), baseObj.get(o.getKey())).compare(o.getValue(), baseObj.get(o.getKey()), parentNode, "");
								
				if (!curRes) {
					noChange = false;
				}
				
			} else {
				JocNodeUtils.createMissingJocNode(o, parentNode);
				noChange = false;
			}
			
		}
		
		return noChange;
		
	}


	public static boolean compareOtherWay(Map<Object, Object> newObj, Map<Object, Object> baseObj, JocNode parentNode) {
		
		boolean noChange = true;
		
		for (Entry<Object, Object> o : baseObj.entrySet()) {
			
			if (!newObj.containsKey(o.getKey())) {
				JocNodeUtils.createUnexpectedJocNode(o, parentNode);
				noChange = false;
			} 
			
		}
		
		return noChange;
		
	}

}
