package local.study.metrics;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import local.study.metrics.document.Document;
import local.study.metrics.document.Row;

public class Matrix {

	private Document document;
	private int[][] matrix;

	public Matrix(Document document) {
		this.document = document;
		this.matrix = new int[getNames().size()][Metric.values().length];
		fill();
	}

	private List<String> getNames() {
		return document.getRows().stream().skip(1).map(row -> row.getColumnValue(document.getCtx().getNameColumnName()))
				.distinct().sorted().collect(Collectors.toList());
	}

	private void fill() {
		for (Row row : document.fillMetrics()) {
			Metric metric = Metric.valueOf(row.getColumnValue(Constants.METRIC));
			int x = metric.index();
			int y = getNames().indexOf(row.getColumnValue(document.getCtx().getNameColumnName()));
			matrix[y][x] += 1;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int maxNameLength = getNames().stream().mapToInt(String::length).max().getAsInt();
		sb.append(String.format("%1$" + maxNameLength + "s", " "));
		
		Arrays.stream(Metric.values()).forEach(ele -> {
			sb.append("   ").append(ele).append("  ");
		});

		sb.append("\n");

		Queue<String> names = new LinkedList<>(getNames());
		for (int i = 0; i < matrix.length; i++) {
			int[] row = matrix[i];

			sb.append(names.poll());

			Arrays.stream(row).forEach(ele -> {
				sb.append("   ").append(ele).append("   ");
			});
			sb.append("\n");
		}

		return sb.toString();
	}

	public double precision(int rowIndex) {
		double tp = getTP(rowIndex);
		double fp = getFP(rowIndex);
		double fp_fn = getFP_FN(rowIndex);
		return asPercent(tp / (tp + fp + fp_fn));
	}

	public double recall(int rowIndex) {
		double tp = getTP(rowIndex);
		double fn = getFN(rowIndex);
		double fp_fn = getFP_FN(rowIndex);
		return asPercent(tp / (tp + fn + fp_fn));
	}

	private double getTP(int rowIndex) {
		return matrix[rowIndex][Metric.TP.index()];
	}

	private double getFP(int rowIndex) {
		return matrix[rowIndex][Metric.FP.index()];
	}

	private double getFN(int rowIndex) {
		return matrix[rowIndex][Metric.FN.index()];
	}

	private double getFP_FN(int rowIndex) {
		return matrix[rowIndex][Metric.FP_FN.index()];
	}

	private double asPercent(double val) {
		return val * 100;
	}
}
