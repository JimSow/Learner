package Learner.PoolLearning.UncertaintySampling;

import Learner.PoolLearning.APoolLearner;
import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

import java.util.Map;

/**
 * Created by jared on 11/2/15.
 */
public class LeastConfident extends APoolLearner {

	public LeastConfident(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData,
							  StopCondition pTarget, int pInitialTestsToRun) {
		super(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun);
	}

	/*
	 * This strategy prefers the instance whose most likely labeling is actually the least likely
  	 * among th unlabeled instances available for querying.
  	 */
	@Override
	protected int getNextInstanceIndex() {
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
}
