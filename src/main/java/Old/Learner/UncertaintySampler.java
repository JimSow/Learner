package Old.Learner;

import Learner.PoolLearning.APoolLearner;
import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by jared on 10/31/15.
 */
public class UncertaintySampler extends APoolLearner {

	public UncertaintySampler(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData,
							  StopCondition pTarget, int pInitialTestsToRun) {
		super(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun);
	}

	@Override
	protected int getNextInstanceIndex() {
		return leastConfident();
	}

	/*
 	 * This strategy prefers the instance whose most likely labeling is actually the least likely
 	 * among th unlabeled instances available for querying.
 	 */
	private int leastConfident() {
		double minCertainty = Double.MAX_VALUE;
		int index = 0;

		int size = unlabeledData.size();
		for(int i = 0; i < size; ++i) {
			Map<Object, Double> map = classifier.classDistribution(unlabeledData.get(i));

			double max = maxCertainty(map);

			if(max < minCertainty) {
				minCertainty = max;
				index = i;
			}
		}
		return index;
	}

	private double maxCertainty(Map<Object, Double> map) {
		double total = 0;
		double max = 0;

		for(Object key : map.keySet()) {
			total += map.get(key);
		}
		for(Object key : map.keySet()) {
			max = Math.max(max, map.get(key) / total);
		}
		return max;
	}

	private int margin() {
		double minMargin = Double.MAX_VALUE;
		int index = 0;

		int size = unlabeledData.size();
		for(int i = 0; i < size; ++i) {
			Map<Object, Double> map = classifier.classDistribution(unlabeledData.get(i));

			double margin = getMargin(map);

			if(margin < minMargin) {
				minMargin = margin;
				index = i;
			}
		}
		return index;
	}

	private double getMargin(Map<Object, Double> map) {
		int size = map.size();

		if(size <= 1)
			return 1;

		ArrayList<Double> items = new ArrayList<Double>(size);

		double total = 0;

		for(Object key : map.keySet()) {
			total += map.get(key);
		}
		for(Object key : map.keySet()) {
			items.add(map.get(key) / total);
		}

		Collections.sort(items);

		return items.get(0) - items.get(1);
	}


	private int entropy() {
		double maxEntropy = Double.MIN_VALUE;
		int index = 0;

		int size = unlabeledData.size();
		for(int i = 0; i < size; ++i) {
			Map<Object, Double> map = classifier.classDistribution(unlabeledData.get(i));

			double entropy = getEntropy(map);

			if(entropy > maxEntropy) {
				maxEntropy = entropy;
				index = i;
			}
		}
		return index;
	}

	private double getEntropy(Map<Object, Double> map) {
		double total = 0;
		double entropy = 0;

		for(Object key : map.keySet()) {
			total += map.get(key);
		}
		for(Object key : map.keySet()) {
			double y = map.get(key) / total;
			entropy += y * Math.log(y)/Math.log(2);
		}

		return -entropy;
	}
}
