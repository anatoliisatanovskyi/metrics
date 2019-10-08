package local.study.metrics.document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Document {

	private final Context ctx;
	private final List<Row> rows;

	private Document(Context ctx, List<Row> rows) {
		this.ctx = ctx;
		this.rows = rows;
	}

	public List<Row> fillMetrics() {
		return rows.stream().skip(1).map(row -> row.fillMetric(ctx)).collect(Collectors.toList());
	}

	public Context getCtx() {
		return ctx;
	}

	public List<Row> getRows() {
		return rows;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Context ctx;
		private List<Row> rows = new ArrayList<>();

		public Document build() {
			return new Document(ctx, rows);
		}

		public Builder context(Context ctx) {
			this.ctx = ctx;
			return this;
		}

		public Builder addRow(Row row) {
			this.rows.add(row);
			return this;
		}
	}
}
