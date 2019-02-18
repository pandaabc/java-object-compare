package engine.comparator;

import java.awt.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import compare.JocNode;
import engine.CompareFactory;
import interfaces.ICompare;

public abstract class AbstractComparator implements ICompare {
	
	private static Set<Class<?>> collectionClass;
	
	public JocNode constructJocNode(Object newObj, Object baseObj, JocNode parentNode, String path, Annotation... annotations) {
		
		JocNode curNode = new JocNode(newObj, baseObj, path);
		curNode.setParent(parentNode);
		parentNode.addChild(curNode);
		if (isCollection(parentNode)) {
			curNode.setIndex(parentNode.getAndIncrementChildIndex());
		}
		curNode.setAnnotations(annotations);
		
		return curNode;
		
	}
	
	protected boolean isCollection(JocNode node) {
		
		return (node.getNewObject() != null && isCollectionClass(node.getNewObject().getClass()));
		
	}
	
	protected static boolean isCollectionClass(Class<?> clazz) {
		
		if (collectionClass == null || collectionClass.isEmpty()) {
			collectionClass = new HashSet<>();
			collectionClass.add(Set.class);
			collectionClass.add(List.class);
			collectionClass.add(Map.class);
			collectionClass.add(HashSet.class);
			collectionClass.add(ArrayList.class);
			collectionClass.add(HashMap.class);
			collectionClass.add(Array.class);
		}
		
		return collectionClass.contains(clazz);
		
	}

}
