package engine;

import java.util.List;
import java.util.stream.Collectors;

import compare.JocNode;
import compare.utils.JocNodeUtils;
import constant.Status;
import engine.report.JocReport;
import engine.report.JocReportNode;
import interfaces.ICompare;

public class JocCompare {
	
	CompareFactory compareFactory;
	ReportFactory reportFactory;
	Object newObj;
	Object baseObj;
	JocNode node;
	String compareName;
	
	private JocCompare(CompareFactory compareFactory, ReportFactory reportFactory, Object newObj, Object baseObj, String compareName) {
		this.compareFactory = compareFactory;
		this.reportFactory = reportFactory;
		this.newObj = newObj;
		this.baseObj = baseObj;
		this.compareName = compareName;
	}
	
	public static Builder getBuilder() {
		return new Builder();
	}
	
	public JocCompare compare() {
		JocNode parent = JocNodeUtils.createParentNode();
		compareFactory.getComparator(newObj, baseObj).compare(newObj, baseObj, parent, compareName);
		node = parent.getChildren().get(0);
		node.setParent(null);
		return this;
	}
	
	public JocNode getJocNode() {
		return node;
	}
	
	public JocReport reportAll() {
		List<JocReportNode> reportList = getJocNode()
										.getAllNodes()
										.stream()
										.map(n -> reportFactory.getReportFormatter(n.getBaseObject() == null ? n.getNewObject().getClass() : n.getBaseObject().getClass()).format(n))
										.collect(Collectors.toList());
		JocReport report = new JocReport();
		report.setReports(reportList);
		return report;
	}
	
	public JocReport reportDiff() {
		List<JocReportNode> reportList = getJocNode()
										.getAllNodes()
										.stream()
										.filter(n -> !Status.NOCHANGE.equals(n.getStatus()))
										.map(n -> reportFactory.getReportFormatter(n.getBaseObject() == null ? n.getNewObject().getClass() : n.getBaseObject().getClass()).format(n))
										.collect(Collectors.toList());
		JocReport report = new JocReport();
		report.setReports(reportList);
		return report;
	}
	
	public static class Builder{
		
		CompareFactory compareFactory = new CompareFactory();
		ReportFactory reportFactory = new ReportFactory();
		Object newObj;
		Object baseObj;
		String compareName;
		
		
		public Builder setCompareFactory(CompareFactory compareFactory) {
			this.compareFactory = compareFactory;
			return this;
		}
		
		public Builder setReportFactory(ReportFactory reportFactory) {
			this.reportFactory = reportFactory;
			return this;
		}
		
		public Builder setNewObj(Object newObj) {
			this.newObj = newObj;
			return this;
		}
		
		public Builder setBaseObj(Object baseObj) {
			this.baseObj = baseObj;
			return this;
		}
		
		public Builder setCompareName(String compareName) {
			this.compareName = compareName;
			return this;
		}
		
		public JocCompare build() {
			if (compareName == null) {
				this.compareName = newObj == null ? (baseObj == null ? "null" : baseObj.getClass().getSimpleName()) : newObj.getClass().getSimpleName();
			}
			return new JocCompare(compareFactory, reportFactory, newObj, baseObj, compareName);
		}
		
		public JocCompare buildDefaultJocCompare(Object newObj, Object baseObj) {
			this.newObj = newObj;
			this.baseObj = baseObj;
			this.compareName = newObj == null ? (baseObj == null ? "null" : baseObj.getClass().getSimpleName()) : newObj.getClass().getSimpleName();
			return build();
		}
		
		public Builder addClassComparator(Class<?> clazz, ICompare comparator) {
			this.compareFactory.addClassComparator(clazz, comparator);
			return this;
		}
				
	}
	
	
}
