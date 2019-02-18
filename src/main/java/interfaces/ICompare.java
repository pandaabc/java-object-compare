package interfaces;

import java.lang.annotation.Annotation;

import compare.JocNode;

public interface ICompare {
	public boolean compare(Object newObj, Object baseObj, JocNode parentNode, String path, Annotation... annotation);
}
