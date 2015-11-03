package Tests.LearnerTests;

import Learner.*;
import Old.Learner.UncertaintySampler;
import Oracle.IOracle;
import Oracle.SimpleOracle;
import be.abeel.util.Pair;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.sampling.Sampling;
import net.sf.javaml.tools.data.ARFFHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by jared on 10/15/15.
 */
public class LearnerTest {
	public static void main(String[] argc) {

		try {
			String dataFile = "data/iris.arff";
			int classLoc = 4;
			int numItter = 1000;

			StopCondition st1 = new StopCondition(150);
			StopCondition st2 = new StopCondition(3, 100, .94);

//			runBoth   (dataFile, classLoc, numItter);
			runPassive(dataFile, classLoc, numItter, st2);
			runActive (dataFile, classLoc, numItter, st2);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void runActive (String fileLocation, int classLoc, int pNumItter, StopCondition st) throws FileNotFoundException {
		System.out.println("Running Active Learner");
		double averageAccuracyActive  = 0;
		double averageNumTestsActive  = 0;

		double numItter = pNumItter;

		while(pNumItter > 0) {
			Dataset data = ARFFHandler.loadARFF(new File(fileLocation), classLoc);

			NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
			nmr.build(data);
			nmr.filter(data);

			Sampling s = Sampling.SubSampling;

			Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.7), 1);

			data = data2.x();
			Dataset testData = data2.y();

			Classifier knn = new KNearestNeighbors(5);
//			knn = new KNN(5, new EuclideanDistance());

			IOracle oracle = new SimpleOracle(data);

			ILearner l1 = new UncertaintySampler(oracle, knn, data, testData, st, 10);

			l1.run();
			ResultSet rs1 = l1.getResults();

//			System.out.println("Completed test #" + (numItter - pNumItter));
//			System.out.println("Active Results");
//			System.out.println("Accuracy: " + rs1.getAccuracy());
//			System.out.println("NumTests: " + rs1.getNumTests());

			averageAccuracyActive  += rs1.getAccuracy();
			averageNumTestsActive  += rs1.getNumTests();

			--pNumItter;
		}

		averageAccuracyActive  /= numItter;
		averageNumTestsActive  /= numItter;

		System.out.println("Number of Learners Trained: " + numItter );
		System.out.println("Active Results");
		System.out.println("Average Accuracy          : " + averageAccuracyActive + "%");
		System.out.println("Average Number of Tests   : " + averageNumTestsActive      );
	}

	public static void runPassive(String fileLocation, int classLoc, int pNumItter, StopCondition st) throws FileNotFoundException {
		System.out.println("Running Passive Learner");
		double averageAccuracyPassive = 0;
		double averageNumTestsPassive = 0;

		double numItter = pNumItter;

		while(pNumItter > 0) {
			Dataset data = ARFFHandler.loadARFF(new File(fileLocation), classLoc);

			NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
			nmr.build(data);
			nmr.filter(data);

			Sampling s = Sampling.SubSampling;

			Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.7), 1);

			data = data2.x();
			Dataset testData = data2.y();

			Classifier knn = new KNearestNeighbors(5);

			IOracle oracle = new SimpleOracle(data);

			ILearner l2 = new PassiveLearner(oracle, knn, data, testData, st, 10);

			l2.run();
			ResultSet rs2 = l2.getResults();

//			System.out.println("Completed test #" + (numItter - pNumItter));
//			System.out.println("Passive Results");
//			System.out.println("Accuracy: " + rs2.getAccuracy());
//			System.out.println("NumTests: " + rs2.getNumTests());

			averageAccuracyPassive += rs2.getAccuracy();
			averageNumTestsPassive += rs2.getNumTests();

			--pNumItter;
		}
		averageAccuracyPassive /= numItter;
		averageNumTestsPassive /= numItter;

		System.out.println("Number of Learners Trained: " + numItter );
		System.out.println("Passive Results");
		System.out.println("Average Accuracy          : " + averageAccuracyPassive + "%");
		System.out.println("Average Number of Tests   : " + averageNumTestsPassive      );

	}

	public static void runBoth   (String fileLocation, int classLoc, int pNumItter, StopCondition st) throws FileNotFoundException {
		System.out.println("Running Both Learners");
		double averageAccuracyActive  = 0;
		double averageNumTestsActive  = 0;
		double averageAccuracyPassive = 0;
		double averageNumTestsPassive = 0;

		double numItter = pNumItter;

		while(pNumItter > 0) {
			Dataset data = ARFFHandler.loadARFF(new File(fileLocation), classLoc);

			NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
			nmr.build(data);
			nmr.filter(data);

			Sampling s = Sampling.SubSampling;


			Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.7), 1);

			data = data2.x();
			Dataset testData = data2.y();

			Classifier knn = new KNearestNeighbors(5);

			IOracle oracle = new SimpleOracle(data);

			ILearner l1 = new PassiveLearner(oracle, knn, data, testData, st, 10);
			ILearner l2 = new PassiveLearner(oracle, knn, data, testData, st, 10);

			l1.run();
			l2.run();
			ResultSet rs1 = l1.getResults();
			ResultSet rs2 = l2.getResults();

			System.out.println("Completed test #" + (numItter - pNumItter));
			System.out.println("Active Results");
			System.out.println("Accuracy: " + rs1.getAccuracy());
			System.out.println("NumTests: " + rs1.getNumTests());
			System.out.println("Passive Results");
			System.out.println("Accuracy: " + rs2.getAccuracy());
			System.out.println("NumTests: " + rs2.getNumTests());

			averageAccuracyActive  += rs1.getAccuracy();
			averageNumTestsActive  += rs1.getNumTests();
			averageAccuracyPassive += rs2.getAccuracy();
			averageNumTestsPassive += rs2.getNumTests();

			--pNumItter;
		}

		averageAccuracyActive  /= numItter;
		averageNumTestsActive  /= numItter;
		averageAccuracyPassive /= numItter;
		averageNumTestsPassive /= numItter;

		System.out.println("Number of Learners Trained: " + numItter );
		System.out.println("Active Results");
		System.out.println("Average Accuracy          : " + averageAccuracyActive + "%");
		System.out.println("Average Number of Tests   : " + averageNumTestsActive      );
		System.out.println("Passive Results");
		System.out.println("Average Accuracy          : " + averageAccuracyPassive + "%");
		System.out.println("Average Number of Tests   : " + averageNumTestsPassive      );

	}

	public static void simpleTest(Classifier c, Dataset data, Dataset testData) {
		c.buildClassifier(data);

		Map<Object, PerformanceMeasure> map = EvaluateDataset.testDataset(c, testData);

		double accuracy = 0;
		int i = 0;

		for(Object o:map.keySet()) {

			accuracy += map.get(o).getAccuracy();
			i++;

			System.out.println(o + " Accuracy: " + map.get(o).getAccuracy());
			System.out.println(o + " BCR: " + map.get(o).getBCR());
			System.out.println(o + " Correlation: " + map.get(o).getCorrelation());
			System.out.println(o + " Correlation Coefficient: " + map.get(o).getCorrelationCoefficient());
			System.out.println(o + " Cost: " + map.get(o).getCost());
			System.out.println(o + " Error rate: " + map.get(o).getErrorRate());
			System.out.println(o + " F Measure: " + map.get(o).getFMeasure());
			System.out.println(o + " FN Rate: " + map.get(o).getFNRate());
			System.out.println(o + " FP Rate: " + map.get(o).getFPRate());
			System.out.println(o + " Precision: " + map.get(o).getPrecision());
			System.out.println(o + " Q9: " + map.get(o).getQ9());
			System.out.println(o + " Recall: " + map.get(o).getRecall());
			System.out.println(o + " TN Rate: " + map.get(o).getTNRate());
			System.out.println(o + " Total: " + map.get(o).getTotal());
			System.out.println(o + " TP Rate: " + map.get(o).getTPRate());
			System.out.println();
			System.out.println();
		}

		accuracy /= i;

		System.out.println("Total Accuracy: "  + accuracy);
	}

}
