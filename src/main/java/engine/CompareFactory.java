package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import compare.annotation.JocComparator;
import compare.exception.UnexpectedComparatorException;
import engine.comparator.DefaultComparator;
import engine.comparator.ListComparator;
import engine.comparator.MapComparator;
import engine.comparator.SetComparator;
import engine.comparator.SimpleObjectComparator;
import interfaces.ICompare;

public class CompareFactory {
	
	private Map<Class<?>, ICompare> comparators;
		
	public ICompare getComparator(Object newObj, Object baseObj) {
		
		if (newObj == null || baseObj == null || !newObj.getClass().equals(baseObj.getClass())){
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
		
		return getComparators().containsKey(newObj.getClass()) ? getComparators().get(newObj.getClass()) : getComparators().get(DefaultComparator.class);
		
	}
	
	private Map<Class<?>, ICompare> getComparators() {
		
		if (comparators == null) {
			setupComparators();
		}
		
		return comparators;
		
	}
	
	private void setupComparators() {
		DefaultComparator defaultComparator = new DefaultComparator(this);
		SimpleObjectComparator simpleObjectComparator = new SimpleObjectComparator();
		MapComparator mapComparator = new MapComparator(this);
		SetComparator setComparator = new SetComparator();
		ListComparator listComparator = new ListComparator(this);
		
		comparators = new HashMap<>();
		
		comparators.put(DefaultComparator.class, defaultComparator);
		comparators.put(Boolean.class, simpleObjectComparator);
		comparators.put(Character.class, simpleObjectComparator);
		comparators.put(Byte.class, simpleObjectComparator);
		comparators.put(Short.class, simpleObjectComparator);
		comparators.put(Integer.class, simpleObjectComparator);
		comparators.put(Long.class, simpleObjectComparator);
		comparators.put(Float.class, simpleObjectComparator);
		comparators.put(Double.class, simpleObjectComparator);
		comparators.put(Void.class, simpleObjectComparator);
		comparators.put(String.class, simpleObjectComparator);
		comparators.put(Map.class, mapComparator);
		comparators.put(HashMap.class, mapComparator);
		comparators.put(Set.class, setComparator);
		comparators.put(HashSet.class, setComparator);
		comparators.put(List.class, listComparator);
		comparators.put(ArrayList.class, listComparator);
		
	}
	
	public void addClassComparator(Class<?> clazz, ICompare comparator) {
		
		if (comparators == null) {
			setupComparators();
		}
		
		comparators.put(clazz, comparator);
		
	}
	
}
