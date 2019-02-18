package engine;

import compare.JocNode;
import compare.utils.JocNodeUtils;

public class JocCompare {
	
	CompareFactory compareFactory;
	ReportFactory reportFactory;
	Object newObj;
	Object baseObj;
	
	private JocCompare(CompareFactory compareFactory, ReportFactory reportFactory, Object newObj, Object baseObj) {
		this.compareFactory = compareFactory;
		this.reportFactory = reportFactory;
		this.newObj = newObj;
		this.baseObj = baseObj;
	}
	
	public Builder getBuilder() {
		return new Builder();
	}
	
	public JocNode compare() {
		JocNode parent = JocNodeUtils.createParentNode();
		compareFactory.getComparator(newObj, baseObj).compare(newObj, baseObj, parent, "");
		return parent.getChildren().get(0);
	}
	
	public static class Builder{
		
		CompareFactory compareFactory;
		ReportFactory reportFactory;
		Object newObj;
		Object baseObj;
		
		
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
		
		public JocCompare build() {
			if (compareFactory == null) {
				compareFactory = new CompareFactory();
			}
			if (reportFactory == null) {
				reportFactory = new ReportFactory();
			}
			return new JocCompare(compareFactory, reportFactory, newObj, baseObj);
		}
		
		public JocCompare defaultJocCompare(Object newObj, Object baseObj) {
			return new JocCompare(new CompareFactory(), new ReportFactory(), newObj, baseObj);
		}
				
	}
	
	
}
