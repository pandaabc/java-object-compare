package compare.utils;

import compare.JocNode;
import constant.Status;

public class JocNodeUtils {
	
	
	public static JocNode createMissingJocNode(Object o, JocNode parentNode) {
		JocNode curNode = new JocNode(o, null, "");
		curNode.setIndex(parentNode.getAndIncrementChildIndex());
		parentNode.addChild(curNode);
		curNode.setParent(parentNode);
		curNode.setStatus(Status.MISSING);
		return curNode;
	}
	
	public static JocNode createNoChangeJocNode(Object o, JocNode parentNode) {
		JocNode curNode = new JocNode(o, o, "");
		curNode.setIndex(parentNode.getAndIncrementChildIndex());
		parentNode.addChild(curNode);
		curNode.setParent(parentNode);
		curNode.setStatus(Status.NOCHANGE);
		return curNode;
	}
	
	public static JocNode createUnexpectedJocNode(Object o, JocNode parentNode) {
		JocNode curNode = new JocNode(null, o, "");
		curNode.setIndex(parentNode.getAndIncrementChildIndex());
		parentNode.addChild(curNode);
		curNode.setParent(parentNode);
		curNode.setStatus(Status.UNEXPECTED);
		return curNode;
	}
	
	public static JocNode createParentNode() {
		JocNode curNode = new JocNode(null, null, "");
		return curNode;
	}

}
