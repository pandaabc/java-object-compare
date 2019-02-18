package engine.report;

import java.util.function.Function;

import compare.JocNode;

public enum ReportFormatterFunction {
	SIMPLE_PATH_FORMATTER(node -> node.getPath("/")), 
	SIMPLE_INDEXED_PATH_FORMATTER(node -> node.getPathAndIndex("/")), 
	SIMPLE_MESSAGE_FORMATTER(node -> node.getStatus().toString() + " :: from: " + node.getBaseObject() + " to: " + node.getNewObject());
	
	Function<JocNode, String> function;
	
	ReportFormatterFunction(Function<JocNode, String> function) {
		this.function = function;
	}
	
	public Function<JocNode, String> getFunction() {
		return function;
	}
	
}

