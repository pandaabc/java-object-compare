package engine;

import java.util.HashMap;
import java.util.Map;

import engine.report.ReportConsolidatorFunction;
import engine.report.ReportFormatter;
import engine.report.ReportFormatterFunction;

public class ReportFactory {
	private Map<Class<?>, ReportFormatter> formatters;
	
	public ReportFormatter getReportFormatter(Class<?> clazz) {
		return getReportFormatters().containsKey(clazz) ? getReportFormatters().get(clazz) : getReportFormatters().get(ReportFormatter.class);
	}
	
	protected Map<Class<?>, ReportFormatter> getReportFormatters() {
		
		if (formatters == null) {
			setupFormatters();
		}
		
		return formatters;
		
	}
	
	private void setupFormatters() {
		ReportFormatter defaultReportFormatter = new ReportFormatter(ReportFormatterFunction.SIMPLE_PATH_FORMATTER.getFunction(), 
																	ReportFormatterFunction.SIMPLE_MESSAGE_FORMATTER.getFunction(),
																	ReportConsolidatorFunction.SIMPLE_CONSOLIDATOR.getFunction());
		
		formatters = new HashMap<>();
		
		formatters.put(ReportFormatter.class, defaultReportFormatter);
		
	}
	
	public void addFormatter(Class<?> clazz, ReportFormatter reportFormatter) {
		
		if (formatters == null) {
			setupFormatters();
		}
		
		formatters.put(clazz, reportFormatter);
		
	}
}
