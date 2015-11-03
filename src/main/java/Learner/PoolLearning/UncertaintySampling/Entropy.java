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
public class Entropy extends APoolLearner {

	public Entropy(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData,
							  StopCondition pTarget, int pInitialTestsToRun) {
		super(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun);
	}

	@Override
	protected int getNextInstanceIndex() {
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
