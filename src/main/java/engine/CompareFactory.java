package engine;

import java.util.HashMap;
import java.util.Map;

import compare.annotation.JocComparator;
import compare.exception.UnexpectedComparatorException;
import engine.comparator.DefaultComparator;
import engine.comparator.PrimitiveWrapperComparator;
import interfaces.ICompare;

public class CompareFactory {
	
	private Map<Class<?>, ICompare> comparators;
		
	public ICompare getComparator(Object newObj, Object baseObj) {
		
		if (!newObj.getClass().equals(baseObj.getClass())){
			return getComparators().get(DefaultComparator.class);
		}
		
		JocComparator jc = newObj.getClass().getAnnotation(JocComparator.class);

		if (jc != null) {
			if (!jc.clazz().isAssignableFrom(ICompare.class)) {
				throw new UnexpectedComparatorException();
			}
			getComparators().computeIfAbsent(jc.clazz(), key -> {
				try {
					return (ICompare) key.newInstance();
				} catch (Exception e) {
					
				}
				return null;
			});
			return getComparators().get(jc.clazz());
		}
				
		return getComparators().get(newObj.getClass());
		
	}
	
	private Map<Class<?>, ICompare> getComparators() {
		
		if (comparators == null) {
			setupComparators();
		}
		
		return comparators;
		
	}
	
	private void setupComparators() {
		DefaultComparator defaultComparator = new DefaultComparator();
		PrimitiveWrapperComparator primitiveWrapperComparator = new PrimitiveWrapperComparator();
		
		comparators = new HashMap<>();
		
		comparators.put(DefaultComparator.class, defaultComparator);
		comparators.put(Boolean.class, primitiveWrapperComparator);
		comparators.put(Character.class, primitiveWrapperComparator);
		comparators.put(Byte.class, primitiveWrapperComparator);
		comparators.put(Short.class, primitiveWrapperComparator);
		comparators.put(Integer.class, primitiveWrapperComparator);
		comparators.put(Long.class, primitiveWrapperComparator);
		comparators.put(Float.class, primitiveWrapperComparator);
		comparators.put(Double.class, primitiveWrapperComparator);
		comparators.put(Void.class, primitiveWrapperComparator);
		
	}
	
}
