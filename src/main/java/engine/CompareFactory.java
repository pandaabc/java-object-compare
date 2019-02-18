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
	
	private Map<Class<?>, ICompare> classComparators;
	private Map<String, ICompare> pathComparators;
		
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
		
		if (classComparators == null) {
			setupComparators();
		}
		
		return classComparators;
		
	}
	
	private void setupComparators() {
		DefaultComparator defaultComparator = new DefaultComparator(this);
		SimpleObjectComparator simpleObjectComparator = new SimpleObjectComparator();
		MapComparator mapComparator = new MapComparator(this);
		SetComparator setComparator = new SetComparator();
		ListComparator listComparator = new ListComparator(this);
		
		classComparators = new HashMap<>();
		
		classComparators.put(DefaultComparator.class, defaultComparator);
		classComparators.put(Boolean.class, simpleObjectComparator);
		classComparators.put(Character.class, simpleObjectComparator);
		classComparators.put(Byte.class, simpleObjectComparator);
		classComparators.put(Short.class, simpleObjectComparator);
		classComparators.put(Integer.class, simpleObjectComparator);
		classComparators.put(Long.class, simpleObjectComparator);
		classComparators.put(Float.class, simpleObjectComparator);
		classComparators.put(Double.class, simpleObjectComparator);
		classComparators.put(Void.class, simpleObjectComparator);
		classComparators.put(String.class, simpleObjectComparator);
		classComparators.put(Map.class, mapComparator);
		classComparators.put(HashMap.class, mapComparator);
		classComparators.put(Set.class, setComparator);
		classComparators.put(HashSet.class, setComparator);
		classComparators.put(List.class, listComparator);
		classComparators.put(ArrayList.class, listComparator);
		
	}
	
	public void addPathComparator(String path, ICompare comparator) {
		
		if (pathComparators == null) {
			pathComparators = new HashMap<>();
		}
		
		pathComparators.put(path, comparator);
		
	}
	
	public ICompare getPathComparator(String path) {
		
		return pathComparators == null ? null : pathComparators.get(path);
		
	}
	
	public void addClassComparator(Class<?> clazz, ICompare comparator) {
		
		if (classComparators == null) {
			setupComparators();
		}
		
		classComparators.put(clazz, comparator);
		
	}
	
}
