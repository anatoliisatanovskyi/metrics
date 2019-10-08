package local.study.metrics.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import local.study.metrics.Constants;
import local.study.metrics.document.Context;
import local.study.metrics.document.Document;
import local.study.metrics.document.Row;

public class CsvInput implements Source {

	private File csv;

	public CsvInput(File csv) {
		this.csv = csv;
	}

	@Override
	public Document read() {
		try {
			Document.Builder builder = Document.builder().context(Context.builder().documentId(Constants.DOCUMENT_ID)
					.name(Constants.NAME).gold(Constants.GOLD).extracted(Constants.EXTRACTED).build());
			readLines().stream().map(this::parseRow).forEach(builder::addRow);
			return builder.build();
		} catch (IOException e) {
			throw new IllegalArgumentException("error reading file", e);
		}
	}

	private Row parseRow(String line) {

		List<String> columns = parseColumns(line);

		Row row = new Row();
		String documentId = columns.get(0);
		if (documentId.isEmpty()) {
			throw new IllegalArgumentException("Invalid csv format. Missing required 'documentId' column value");
		}
		row.addColumn(Constants.DOCUMENT_ID, documentId);

		String name = columns.get(1);
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Invalid csv format. Missing required 'name' column value");
		}
		row.addColumn(Constants.NAME, name);

		row.addColumn(Constants.GOLD, columns.get(2));
		row.addColumn(Constants.EXTRACTED, columns.get(3));

		return row;
	}

	private List<String> parseColumns(String line) {
		List<String> columns = new ArrayList<>(Arrays.asList(line.split(",")));
		ensureCapacity(4, columns);
		return columns;
	}

	private void ensureCapacity(int min, List<String> list) {
		if (list.size() < 4) {
			int diff = 4 - list.size();
			for (int i = 0; i < diff; i++) {
				list.add("");
			}
		}
	}

	private List<String> readLines() throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
			br.lines().forEach(lines::add);
		}
		lines.forEach(System.out::println);
		return lines;
	}
}
