package compare.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionUtils {
	
	private ReflectionUtils() {}
	
	public static Set<Method> getAllGetterMethods(Class<?> clazz) {
		
		Set<Method> methods = new HashSet<>();
		Set<String> fields = new HashSet<>();

		while (clazz != null) {
			Set<String> curFields = Arrays.stream(clazz.getDeclaredFields())
										.map(Field :: getName)
										.map(String :: toUpperCase)
										.filter(f -> !fields.contains(f))
										.collect(Collectors.toSet());
			
			Set<Method> curMethod = Arrays.stream(clazz.getMethods())
											.filter(m -> isValidMethod(m, curFields))
											.collect(Collectors.toSet());
			
			fields.addAll(curFields);
			methods.addAll(curMethod);
			
			clazz = clazz.getSuperclass();
		}
		
		return methods;
		
	}
	
	protected static boolean isValidMethod(Method m, Set<String> fields) {
		return (m.getName().startsWith("is") || m.getName().startsWith("get")) 
					&& m.getParameters().length == 0 
					&& !m.getReturnType().equals(Void.class)
					&& (fields.contains(getFieldNameFromMethod(m).toUpperCase()));
	}
	
	protected static boolean isValidMethod(Method m, String field) {
		return (m.getName().startsWith("is") || m.getName().startsWith("get")) 
					&& m.getParameters().length == 0 
					&& !m.getReturnType().equals(Void.class)
					&& (field.equalsIgnoreCase(getFieldNameFromMethod(m)));
	}
	
	public static String getFieldNameFromMethod(Method m) {
		
		if (m.getName().startsWith("is")) {
			return m.getName().replaceFirst("is", "");
		} else if (m.getName().startsWith("get")) {
			return m.getName().replaceFirst("get", "");
		}
		return m.getName();
		
	}
	
	public static Method getMethodFromField(Class<?> clazz, String name) {
		while (clazz != null) {
			boolean hasField = Arrays.stream(clazz.getDeclaredFields())
										.map(Field :: getName)
										.anyMatch(n -> name.equalsIgnoreCase(n));
			
			if (hasField) {
				Optional<Method> methodOptional = Arrays.stream(clazz.getMethods())
											.filter(m -> isValidMethod(m, name))
											.findFirst();
				
				if (methodOptional.isPresent()) {
					return methodOptional.get();
				} 
			}
			
			clazz = clazz.getSuperclass();
		}
		
		return null;
	}

}
