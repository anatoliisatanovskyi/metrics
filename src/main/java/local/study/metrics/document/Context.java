package local.study.metrics.document;

import java.util.HashMap;
import java.util.Map;

import local.study.metrics.Constants;

public class Context {

	private Map<String, String> columnNames;

	private Context(Map<String, String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getDocumentIdColumnName() {
		return columnNames.get(Constants.DOCUMENT_ID);
	}

	public String getNameColumnName() {
		return columnNames.get(Constants.NAME);
	}

	public String getGoldColumnName() {
		return columnNames.get(Constants.GOLD);
	}

	public String getExtractedColumnName() {
		return columnNames.get(Constants.EXTRACTED);
	}

	@Override
	public String toString() {
		return String.format("documentId=%s name=%s gold=%s extracted=%s", getDocumentIdColumnName(),
				getNameColumnName(), getGoldColumnName(), getExtractedColumnName());
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Map<String, String> columnNames = new HashMap<>();

		public Context build() {
			return new Context(columnNames);
		}

		public Builder documentId(String value) {
			columnNames.put(Constants.DOCUMENT_ID, value);
			return this;
		}

		public Builder name(String value) {
			columnNames.put(Constants.NAME, value);
			return this;
		}

		public Builder gold(String value) {
			columnNames.put(Constants.GOLD, value);
			return this;
		}

		public Builder extracted(String value) {
			columnNames.put(Constants.EXTRACTED, value);
			return this;
		}
	}
}
