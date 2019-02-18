package compare.utils;

import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import stubs.StubClass;

public class ReflectionUtilsTest {
		
	@Test
	public void testGetAllGetterMethods() {
		
		Set<Method> methods = ReflectionUtils.getAllGetterMethods(StubClass.class);
		Assert.assertTrue(methods.stream().anyMatch(m -> m.getName().equals("getTest1")));
		Assert.assertTrue(methods.stream().noneMatch(m -> m.getName().equals("getStringTest")));
		
	}
	
	@Test
	public void testGetFieldNameFromMethod() throws NoSuchMethodException, SecurityException {
		
		Method m = StubClass.class.getMethod("getTest1");
		
		Assert.assertEquals("Test1", ReflectionUtils.getFieldNameFromMethod(m));
		
	}
	
}
