package local.study.metrics.document;

import java.util.HashMap;
import java.util.Map;

import local.study.metrics.Constants;
import local.study.metrics.Metric;

public class Row {

	private Map<String, String> columns = new HashMap<>();

	public void addColumn(String name, String value) {
		columns.put(name, value);
	}

	public String getColumnValue(String name) {
		return columns.get(name);
	}

	public Row fillMetric(Context ctx) {
		
		Row withMetric = new Row();
		Map<String, String> withMetricColumns = new HashMap<>(this.columns);
		
		String gold = this.columns.get(ctx.getGoldColumnName());
		String extracted = this.columns.get(ctx.getExtractedColumnName());
		
		withMetricColumns.put(Constants.METRIC, Metric.from(gold, extracted).toString());
		withMetric.columns = withMetricColumns;
		
		return withMetric;
	}
}
