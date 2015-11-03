package Learner.HypothesisSpaceSearch.StreamLearning;

import Learner.ILearner;
import Learner.ResultSet;
import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 * Created by jared on 11/2/15.
 */
public abstract class AStreamLearner implements ILearner {
	protected int numTests;

	protected Classifier classifier;

	protected Dataset labeledData;
	private Dataset testData;

	private StopCondition target;

	private IOracle oracle;

	private ResultSet latestResults;

	public AStreamLearner(Classifier pClassifier, IOracle pOracle, Dataset pTestData, StopCondition pTarget) {
		classifier = pClassifier;
		oracle = pOracle;
		testData = pTestData;
		target = pTarget;
	}

	public Classifier runDataSet(Dataset pData) {
		for(Instance i : pData) {
			runInstance(i);
		}
		return classifier;
	}

	public Classifier run() { return classifier; }

	public Classifier verbouseRun() { return classifier; }

	/*
	 * Runs an instance only if necessary, returns true if run, false otherwise.
	 */
	public Boolean runInstance(Instance pNewInstance) {
		Boolean result = runOne(pNewInstance);

		if(result) {
			pNewInstance.setClassValue(oracle.getLabel(pNewInstance));
			labeledData.add(pNewInstance);
			numTests++;
		}

		return result;
	}

	public void printResult() { latestResults.printResults(); }

	public ResultSet getResults() { return latestResults; }

	public int getNumTests() { return numTests; }

	public double getAccuracy() { return latestResults.getAccuracy(); }

	public double getRecall() { return latestResults.getRecall(); }

	/*
	 * Merely returns whether or not to run the instance, does not run the instance.
	 */
	protected abstract Boolean runOne(Instance pNewInstance);
}
