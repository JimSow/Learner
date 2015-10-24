package Learner;

import Retriever.IRetriever;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

import java.util.Map;

/**
 * Created by jared on 10/15/15.
 */
public class Learner implements Runnable {

	private int numTests;
	private int initialTestsToRun;

	private Classifier test;

	private Dataset labeledData;
	private Dataset testData;

	private StopCondition target;

	private IRetriever oracle;

	private Map<Object, PerformanceMeasure> latestResults;

	public Learner(IRetriever pOracle, Classifier pTest, Dataset pTestData) {
		this(pOracle, pTest, pTestData, new StopCondition(10, 100, .7), 10);
	}

	public Learner(IRetriever pOracle, Classifier pTest, Dataset pTestData, StopCondition pTarget) {
		this(pOracle, pTest, pTestData, pTarget, 10);
	}

	public Learner(IRetriever pOracle, Classifier pTest, Dataset pTestData, StopCondition pTarget, int
			pInitialTestsToRun) {
		test = pTest;
		labeledData = new DefaultDataset();
		testData = pTestData;
		target = pTarget;
		oracle = pOracle;
		initialTestsToRun = pInitialTestsToRun;
	}

	public void run() {
		runInitial(initialTestsToRun);

		do {
			if(!runTest())
				break;

			test.buildClassifier(labeledData);

			latestResults = EvaluateDataset.testDataset(test, testData);

		} while(!target.Stop(latestResults, numTests));
	}

	public void verboseRun() {
		runInitial(initialTestsToRun);

		do {
			if(!runTest())
				break;

			test.buildClassifier(labeledData);

			latestResults = EvaluateDataset.testDataset(test, testData);

			printResultBasic();

		} while(!target.Stop(latestResults, numTests));
	}

	public void printResult() {
		double accuracy = 0;
		double recall   = 0;
		int i = 0;

		for(Object o:latestResults.keySet()) {

			accuracy += latestResults.get(o).getAccuracy();
			recall   += latestResults.get(o).getRecall();
			i++;

			System.out.println(o + " Accuracy: " + latestResults.get(o).getAccuracy());
			System.out.println(o + " BCR: " + latestResults.get(o).getBCR());
			System.out.println(o + " Correlation: " + latestResults.get(o).getCorrelation());
			System.out.println(o + " Correlation Coefficient: " + latestResults.get(o).getCorrelationCoefficient());
			System.out.println(o + " Cost: " + latestResults.get(o).getCost());
			System.out.println(o + " Error rate: " + latestResults.get(o).getErrorRate());
			System.out.println(o + " F Measure: " + latestResults.get(o).getFMeasure());
			System.out.println(o + " FN Rate: " + latestResults.get(o).getFNRate());
			System.out.println(o + " FP Rate: " + latestResults.get(o).getFPRate());
			System.out.println(o + " Precision: " + latestResults.get(o).getPrecision());
			System.out.println(o + " Q9: " + latestResults.get(o).getQ9());
			System.out.println(o + " Recall: " + latestResults.get(o).getRecall());
			System.out.println(o + " TN Rate: " + latestResults.get(o).getTNRate());
			System.out.println(o + " Total: " + latestResults.get(o).getTotal());
			System.out.println(o + " TP Rate: " + latestResults.get(o).getTPRate());
			System.out.println();
			System.out.println();
		}

		accuracy /= i;
		recall   /= i;

		System.out.println("Overall Accuracy: "  + accuracy);
		System.out.println("Overall Recall  : "  + recall  );

		System.out.println("Number of tests run: " + numTests);
		System.out.println();
	}

	public void printResultBasic() {
		double accuracy = 0;
		double recall   = 0;
		int i = 0;

		for(Object o:latestResults.keySet()) {

			accuracy += latestResults.get(o).getAccuracy();
			recall   += latestResults.get(o).getRecall();
			i++;

		}

		accuracy /= i;
		recall   /= i;

		System.out.println("Overall Accuracy: "  + accuracy);
		System.out.println("Overall Recall  : "  + recall  );

		System.out.println("Number of tests run: " + numTests);
	}

	public Map<Object, PerformanceMeasure> getResults() {
		return latestResults;
	}

	public double getAccuracy() {
		double accuracy = 0;
		int i = 0;

		for(Object o:latestResults.keySet()) {

			accuracy += latestResults.get(o).getAccuracy();
			i++;

		}

		return accuracy / i;
	}

	public int getNumTests() {
		return numTests;
	}

	public ResultSet getResultSet() {
		return new ResultSet(latestResults, numTests);
	}

	private boolean runTest() {
		Instance i = oracle.getNext();

		if(i == null)
			return false;

		labeledData.add(i);
		numTests++;

		return true;
	}

	private void runInitial(int numTestsToRun) {
		for(int i = 0; i < numTestsToRun; ++i) {
			runTest();
		}
	}
}
