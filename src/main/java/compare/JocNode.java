package compare;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JocNode {
	Object newObject;
	Object baseObject;
	JocNode parent;
	Set<JocNode> children;
	String currentPath;
	boolean isChanged;
	
	public JocNode(Object newObject, Object baseObject) {
		this.newObject = newObject;
		this.baseObject = baseObject;
	}
}
