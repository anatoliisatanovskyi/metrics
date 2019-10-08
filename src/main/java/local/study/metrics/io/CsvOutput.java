package local.study.metrics.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import local.study.metrics.Constants;
import local.study.metrics.document.Document;

public class CsvOutput implements Target {

	private String path;

	public CsvOutput(String path) {
		this.path = path;
	}

	@Override
	public void write(Document document) {
		try {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			List<String> lines = new ArrayList<>();

			String columnNamesLine = String.join(",", document.getCtx().getDocumentIdColumnName(),
					document.getCtx().getNameColumnName(), document.getCtx().getGoldColumnName(),
					document.getCtx().getExtractedColumnName(), Constants.METRIC);

			lines.add(columnNamesLine);
			lines.addAll(document.fillMetrics().stream().map(row -> {
				return String.join(",", row.getColumnValue(document.getCtx().getDocumentIdColumnName()),
						row.getColumnValue(document.getCtx().getNameColumnName()),
						row.getColumnValue(document.getCtx().getGoldColumnName()),
						row.getColumnValue(document.getCtx().getExtractedColumnName()),
						row.getColumnValue(Constants.METRIC));
			}).collect(Collectors.toList()));

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				for (String line : lines) {
					bw.write(line);
					bw.newLine();
				}
			}

		} catch (IOException e) {
			throw new IllegalArgumentException("An error occurred while writing csv file. " + e.getMessage(), e);
		}
	}
}
