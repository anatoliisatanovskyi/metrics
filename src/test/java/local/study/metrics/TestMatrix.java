package local.study.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import local.study.metrics.document.Document;
import local.study.metrics.io.CsvInput;
import local.study.metrics.io.CsvOutput;
import local.study.metrics.io.Source;
import local.study.metrics.io.Target;

public class TestMatrix {

	@Test
	public void testCsv() throws Exception {

		File csv = prepareTestFile("input.csv");

		Source source = new CsvInput(csv);
		Document document = source.read();

		Target target = new CsvOutput("output.csv");
		target.write(document);

		Matrix matrix = new Matrix(document);
		System.out.println(matrix);
	}

	private File prepareTestFile(String path) throws IOException {

		File csv = new File(path);
		if (csv.exists()) {
			csv.delete();
		}
		csv.createNewFile();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv))) {
			writeNextLine(bw, "documentId,name,gold,extracted");

			writeNextLine(bw, "doc1.html,fistName,John,John");
			writeNextLine(bw, "doc2.html,fistName,Jane,Jan");
			writeNextLine(bw, "doc3.html,fistName,Mitch,");
			writeNextLine(bw, "doc4.html,fistName,,Sarah");

			writeNextLine(bw, "doc1.html,lastName,Doe,Doe");
			writeNextLine(bw, "doc2.html,lastName,Doe,Doe");
			writeNextLine(bw, "doc3.html,lastName,Connor,");
			writeNextLine(bw, "doc4.html,lastName,,Connor");
		}
		return csv;
	}

	private void writeNextLine(BufferedWriter bw, String line) throws IOException {
		bw.write(line);
		bw.newLine();
	}
}
