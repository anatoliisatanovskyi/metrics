package local.study.metrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum Metric {
	TP(0), TN(1), FP(2), FN(3), FP_FN(4);

	static Map<Integer, Metric> indexToElementMap = new HashMap<>();
	static {
		for (Metric ele : Metric.values())
			indexToElementMap.put(ele.index, ele);
	}

	int index;

	Metric(int index) {
		this.index = index;
	}

	public int index() {
		return this.index;
	}

	public static Metric forIndex(int index) {
		Metric type = indexToElementMap.get(index);
		if (type == null)
			throw new IllegalArgumentException("invalid index value " + index);
		return type;
	}

	public static Metric from(String gold, String extracted) {

		if (nullOrEmpty(gold) && nullOrEmpty(extracted)) {
			return TN;
		}

		if (nullOrEmpty(gold) && !nullOrEmpty(extracted)) {
			return FP;
		}

		if (!nullOrEmpty(gold) && nullOrEmpty(extracted)) {
			return FN;
		}

		if (Objects.equals(gold, extracted)) {
			return TP;
		}

		return FP_FN;
	}

	private static boolean nullOrEmpty(String value) {
		return value == null || value.isEmpty();
	}
}
