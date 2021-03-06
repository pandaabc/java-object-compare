package compare.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JocCompareController {
	boolean crossCompare() default false;
	boolean indexDiff() default false;
	String key() default "";
	boolean ignore() default false;
}
