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

	public APoolLearner(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData, StopCondition
			pTarget) {
		this(pOracle, pTest, pUnlabeledData, pTestData, pTarget, 10);
	}

	public APoolLearner(IOracle pOracle, Classifier pTest, Dataset pUnlabeledData, Dataset pTestData, StopCondition pTarget, int
			pInitialTestsToRun) {
		classifier		  = pTest;
		labeledData		  = new DefaultDataset();
		unlabeledData     = pUnlabeledData;
		testData		  = pTestData;
		target			  = pTarget;
		oracle 			  = pOracle;
		initialTestsToRun = pInitialTestsToRun;

		initialize();
	}

	public Classifier run() {
		do {
			Instance next = unlabeledData.remove(getNextInstanceIndex());

			Object label = oracle.getLabel(next);

			next.setClassValue(label);

			labeledData.add(next);

			classifier.buildClassifier(labeledData);

			Map<Object, PerformanceMeasure> results = EvaluateDataset.testDataset(classifier, testData);

			++numTests;

			latestResults = new ResultSet(results, numTests);
		}
		while(!target.Stop(getResults(), numTests));

		return classifier;
	}

	protected abstract int getNextInstanceIndex();

	public Classifier verbouseRun() {
		do {
			Instance next = unlabeledData.remove(getNextInstanceIndex());

			Object label = oracle.getLabel(next);

			next.setClassValue(label);

			labeledData.add(next);

			classifier.buildClassifier(labeledData);


			printResult();
		}
		while(!target.Stop(getResults(), numTests));

		return classifier;
	}

	public void printResult() {
		latestResults.printResults();
	}

	public ResultSet getResults() {
		return latestResults;
	}

	private void initialize() {
		Random rand = new Random();
		for(;initialTestsToRun > 0; initialTestsToRun--) {
			Instance next = unlabeledData.remove(rand.nextInt(unlabeledData.size()));
			Object label = oracle.getLabel(next);
			next.setClassValue(label);
			labeledData.add(next);
			++numTests;
		}

		classifier.buildClassifier(labeledData);
	}
}
