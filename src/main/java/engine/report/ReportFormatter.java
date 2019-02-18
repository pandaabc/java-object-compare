package engine.report;

import java.util.function.BiFunction;
import java.util.function.Function;

import compare.JocNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportFormatter {
	Function<JocNode, String> pathFormatter;
	Function<JocNode, String> messageFormatter;
	BiFunction<String, String, String> messageConsolidator;
	
	public JocReportNode format(JocNode node) {
		JocReportNode reportNode = new JocReportNode();
		reportNode.setPath(pathFormatter.apply(node));
		reportNode.setMessage(messageFormatter.apply(node));
		reportNode.setFormattedSummary(messageConsolidator.apply(reportNode.getPath(), reportNode.getMessage()));
		return reportNode;
	}
}
