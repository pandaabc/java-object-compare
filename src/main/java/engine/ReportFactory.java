package engine;

import java.util.HashMap;
import java.util.Map;

import engine.report.ReportFormatter;
import engine.report.ReportFormatterFunction;

public class ReportFactory {
	private Map<Class<?>, ReportFormatter> classFormatters;
	
	protected Map<Class<?>, ReportFormatter> getReportFormatters() {
		
		if (classFormatters == null) {
			setupFormatters();
		}
		
		return classFormatters;
		
	}
	
	private void setupFormatters() {
		ReportFormatter defaultReportFormatter = new ReportFormatter(ReportFormatterFunction.SIMPLE_PATH_FORMATTER.getFunction(), ReportFormatterFunction.SIMPLE_MESSAGE_FORMATTER.getFunction());
		
		classFormatters = new HashMap<>();
		
		classFormatters.put(ReportFormatter.class, defaultReportFormatter);
		
	}
	
	public void addClassComparator(Class<?> clazz, ReportFormatter reportFormatter) {
		
		if (classFormatters == null) {
			setupFormatters();
		}
		
		classFormatters.put(clazz, reportFormatter);
		
	}
}
