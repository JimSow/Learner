package Learner.HypothesisSpaceSearch;

import Learner.PoolLearning.APoolLearner;
import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.weka.WekaClassifier;

import weka.classifiers.functions.LibSVM;

/**
 * Created by jared on 11/2/15.
 */
public class Binary extends APoolLearner {

	public Binary(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData,
				   StopCondition pTarget, int pInitialTestsToRun) {
		super(pOracle, pTest, pUnlabeledData, pTestData, pTarget, pInitialTestsToRun);
	}

	@Override
	protected int getNextInstanceIndex() {

		WekaClassifier wc = new WekaClassifier(new LibSVM());



		return 0;
	}
}
