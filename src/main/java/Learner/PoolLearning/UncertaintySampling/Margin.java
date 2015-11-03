package Learner.PoolLearning.UncertaintySampling;

import Learner.PoolLearning.APoolLearner;
import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by jared on 11/2/15.
 */
public class Margin extends APoolLearner {

	public Margin(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData,
							  StopCondition pTarget, int pInitialTestsToRun) {
		super(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun);
	}

	@Override
	protected int getNextInstanceIndex() {
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
}
