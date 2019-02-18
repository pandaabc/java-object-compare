package compare;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import constant.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JocNode {
	Object newObject;
	Object baseObject;
	JocNode parent;
	List<JocNode> children;
	String currentPath;
	List<String> errorMessage;
	int index = 0;
	int childIndexCount = 0;
	Status status;
	Annotation[] annotations;
	
	public JocNode(Object newObject, Object baseObject, String path) {
		this.newObject = newObject;
		this.baseObject = baseObject;
		this.currentPath = path;
	}
	
	public void addChild(JocNode node) {
		
		if (children == null) {
			children = new ArrayList<>();
		}
		
		children.add(node);
		
	}
	
	public synchronized int getAndIncrementChildIndex() {
		childIndexCount ++;
		return childIndexCount - 1;
	}
	
	public void addErrorMessage(String message) {
		if (errorMessage == null) {
			errorMessage = new ArrayList<>();
		}
		errorMessage.add(message);
	}
	
	public String getPath(String separator) {
		if (this.parent != null) {
			return this.parent.getPath(separator) + separator + this.getCurrentPath();
		}
		return this.getCurrentPath();
	}
	
	public String getPathAndIndex(String separator) {
		String parentPath = null;
		if (this.parent != null) {
			parentPath = this.parent.getPath(separator) + separator;
		}
		String thisPath = this.getCurrentPath().isEmpty() ? String.valueOf(this.getIndex()) : this.getCurrentPath();
		return parentPath == null ? thisPath : parentPath + thisPath;
	}
	
}
