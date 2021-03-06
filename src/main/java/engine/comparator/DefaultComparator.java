package engine.comparator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;

import compare.JocNode;
import compare.utils.ReflectionUtils;
import constant.Status;
import engine.CompareFactory;

public class DefaultComparator extends AbstractComparator {

	CompareFactory factory;
	
	public DefaultComparator(CompareFactory factory) {
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
		} else if (!newObj.getClass().equals(baseObj.getClass())){
			noChange = false;
		} else {
		
			Set<Method> allGetterMethod = ReflectionUtils.getAllGetterMethods(newObj.getClass());
			
			for (Method m : allGetterMethod) {
				boolean curRes = false;
				try {
					Object o1 = m.invoke(newObj);
					Object o2 = m.invoke(baseObj);
					curRes = factory.getComparator(o1, o2).compare(o1, o2, curNode, ReflectionUtils.getFieldNameFromMethod(m), m.getAnnotations());
				} catch (Exception e) {
					curNode.addErrorMessage(ExceptionUtils.getStackTrace(e));
				}
				if (!curRes) {
					noChange = false;
				}
			}
			
		}
		
		curNode.setStatus(noChange ? Status.NOCHANGE : Status.CHANGE);
		
		return noChange;
	}

}
