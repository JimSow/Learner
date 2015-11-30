package Learner.PoolLearning;

import Learner.ILearner;
import Learner.ResultSet;
import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

import java.util.Map;
import java.util.Random;

/**
 * Created by jared on 10/31/15.
 */
public abstract class APoolLearner implements ILearner {

	protected int numTests;
	private   int initialTestsToRun;

	protected Classifier classifier;

	protected Dataset unlabeledData;
	protected Dataset labeledData;
	protected Dataset testData;

	protected StopCondition target;

	protected IOracle oracle;

	private ResultSet latestResults;

	public int    getNumTests() { return numTests;                    }
	public double getAccuracy() { return latestResults.getAccuracy(); }
	public double getRecall  () { return latestResults.getRecall  (); }

//	public APoolLearner(IOracle pOracle, Classifier pTest, Dataset pTestData) {
//		this(pOracle, pTest, pTestData, new StopCondition(10, 100, .7), 10);
//	}

	public APoolLearner(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData,
						StopCondition pTarget) { this(pOracle, pTest, pUnlabeledData, pTestData, pTarget, 10); }

	public APoolLearner(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData, StopCondition pTarget, int
			pInitialTestsToRun) {
		numTests          = 0;
		classifier		  = pTest;
		labeledData		  = new DefaultDataset();
		unlabeledData     = pUnlabeledData.copy();
		testData		  = pTestData.copy();
		target			  = pTarget;
		oracle 			  = pOracle;
		initialTestsToRun = pInitialTestsToRun;

		initialize();
	}

	public Classifier run() {
		do {
			getNext();

			latestResults = test();
		}
		while(!target.Stop(getResults(), numTests) && unlabeledData.size() > 0);

		return classifier;
	}

	protected abstract int getNextInstanceIndex();

	public void printResult() {
		latestResults.printResults();
	}

	public ResultSet getResults() {
		return latestResults;
	}

	private void initialize() {
		Random rand = new Random();
		for(;initialTestsToRun > 0; initialTestsToRun--) {
//			Instance next = unlabeledData.remove(rand.nextInt(unlabeledData.size()));
//			Object label = oracle.getLabel(next);
//			next.setClassValue(label);
//			labeledData.add(next);
			getNext();
		}

		classifier.buildClassifier(labeledData);
	}

	protected void getNext() {
		int nextIndex = getNextInstanceIndex();

		Instance next = unlabeledData.remove(nextIndex);

		Object label = oracle.getLabel(nextIndex);

		next.setClassValue(label);

		labeledData.add(next);

		++numTests;
	}

	private ResultSet test() {
		classifier.buildClassifier(labeledData);

		Map<Object, PerformanceMeasure> results = EvaluateDataset.testDataset(classifier, testData);

		return new ResultSet(results, numTests);
	}
}
