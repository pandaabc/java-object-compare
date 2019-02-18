package engine.report;

import java.util.function.BiFunction;

public enum ReportConsolidatorFunction {
	SIMPLE_CONSOLIDATOR((path, message) -> path + " : " + message);
	
	BiFunction<String, String, String> function;
	
	ReportConsolidatorFunction(BiFunction<String, String, String> function) {
		this.function = function;
	}
	
	public BiFunction<String, String, String> getFunction() {
		return function;
	}
	
}
