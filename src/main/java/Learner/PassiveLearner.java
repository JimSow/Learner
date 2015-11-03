package Learner;

import Learner.PoolLearning.APoolLearner;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

import java.util.Random;

/**
 * Created by jared on 10/31/15.
 */
public class PassiveLearner extends APoolLearner {

	Random rand;

	public PassiveLearner(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData, StopCondition pTarget
			, int pInitialTestsToRun) {
		this(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun, new Random());
	}

	public PassiveLearner(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData, StopCondition pTarget
			, int pInitialTestsToRun, Random pRand) {
		super(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun);

		rand = pRand;
	}

	@Override
	protected int getNextInstanceIndex() {
		return rand.nextInt(unlabeledData.size());
	}
}
