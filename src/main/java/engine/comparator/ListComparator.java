package engine.comparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import compare.JocNode;
import compare.annotation.JocCompareController;
import compare.utils.JocNodeUtils;
import compare.utils.ReflectionUtils;
import constant.Status;
import engine.CompareFactory;

public class ListComparator extends AbstractComparator {
	
	protected Predicate<Annotation> isJocCompareControl = annotation -> JocCompareController.class.equals(annotation.annotationType());
	protected Predicate<Annotation[]> containsJocCompareControl = annotations -> Arrays.stream(annotations).anyMatch(isJocCompareControl);

	CompareFactory factory;
	
	public ListComparator(CompareFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public boolean compare(Object newObj, Object baseObj, JocNode parentNode, String path, Annotation... annotations) {
		
		boolean noChange = true;
		
		List<Object> newObjList = (List<Object>) newObj;
		List<Object> baseObjList = (List<Object>) baseObj;
		
		if (ArrayUtils.isEmpty(annotations) || Arrays.stream(annotations).noneMatch(isJocCompareControl)) {
			JocNode curNode = constructJocNode(newObj, baseObj, parentNode, path, annotations);
			noChange = compareOrderred(newObjList, baseObjList, curNode);
			curNode.setStatus(noChange ? Status.NOCHANGE : Status.CHANGE);
			return noChange;
		} else {
			noChange = crossCompare(newObjList, baseObjList, parentNode, path, annotations);
			return noChange;
		}
		
	}
	
	protected boolean compareOrderred(List<Object> newObjList, List<Object> baseObjList, JocNode curNode) {
		boolean noChange = true;
		for (int i = 0; i < Math.max(newObjList.size(), baseObjList.size()); i ++) {
			
			Object o1 = i >= newObjList.size() ? null : newObjList.get(i);
			Object o2 = i >= baseObjList.size() ? null : baseObjList.get(i);
			
			if (o1 == null) {
				JocNodeUtils.createUnexpectedJocNode(o2, curNode);
				noChange = false;
			} else if (o2 == null) {
				JocNodeUtils.createMissingJocNode(o1, curNode);
			} else {
				boolean curRes = factory.getComparator(o1, o2).compare(o1, o2, curNode, "");
				if (!curRes) {
					noChange = false;
				}
			}
			
		}
		return noChange;
	}
	
	protected boolean crossCompare(List<Object> newObjList, List<Object> baseObjList, JocNode parentNode, String path, Annotation... annotations) {
		
		Optional<Annotation> controlAnnotationOptional = Arrays.stream(annotations).filter(isJocCompareControl).findFirst();
		if (isCrossCompare(controlAnnotationOptional)) {
			return crossCompareInternal(newObjList, baseObjList, parentNode, (JocCompareController)controlAnnotationOptional.get());
		} else {
			JocNode curNode = constructJocNode(newObjList, baseObjList, parentNode, path, annotations);
			boolean noChange = compareOrderred(newObjList, baseObjList, curNode);
			curNode.setStatus(noChange ? Status.NOCHANGE : Status.CHANGE);
			return noChange;
		}
		
	}
	
	protected boolean isCrossCompare(Optional<Annotation> controlAnnotationOptional) {
		return controlAnnotationOptional.isPresent() && ((JocCompareController)controlAnnotationOptional.get()).crossCompare();
	}
	
	protected boolean crossCompareInternal(List<Object> newObjList, List<Object> baseObjList, JocNode parentNode, JocCompareController annotation) {
		
		if (newObjList.isEmpty() && baseObjList.isEmpty()) {
			return true;
		}
		
		Method keyMethod = null;
		
		if (!annotation.key().isEmpty()) {
			keyMethod = ReflectionUtils.getMethodFromField(newObjList.isEmpty() ? baseObjList.get(0).getClass() : newObjList.get(0).getClass(), annotation.key());
		}
		
		if (keyMethod == null) {
			return factory.getComparator(new HashSet<>(), new HashSet<>()).compare(new HashSet<>(newObjList), new HashSet<>(baseObjList), parentNode, "");
		} else {
			Map<Object, Object> objMap1 = convertToMap(newObjList, keyMethod);
			Map<Object, Object> objMap2 = convertToMap(baseObjList, keyMethod);
			return factory.getComparator(objMap1, objMap2).compare(objMap1, objMap2, parentNode, "");
		}
		
	}
	
	protected Map<Object, Object> convertToMap(List<Object> objs, Method m) {
		
		return objs.stream().collect(Collectors.toMap(obj -> {
			try {
				return m.invoke(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}, obj -> obj));
		
	}
	
	
}
