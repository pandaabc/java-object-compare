package engine.report;

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
}
