package Tests.LearnerTests;

import Learner.StopCondition;
import Learner.Learner;
import Oracle.Center;
import Oracle.Passive;
import be.abeel.util.Pair;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.bayes.KDependentBayesClassifier;
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
			Dataset data = ARFFHandler.loadARFF(new File("data/diabetes.arff"), 8);

			NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
			nmr.build(data);
			nmr.filter(data);

			Sampling s = Sampling.SubSampling;


			Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.7), 1);

			data = data2.x();
			Dataset testData = data2.y();

			StopCondition st1 = new StopCondition(data.size());
			StopCondition st2 = new StopCondition(10, data.size(), .74);

			Passive p = new Passive(data, 1);
			Center c = new Center(data);


			Classifier knn = new KNearestNeighbors(5);

			Learner l = new Learner(c, knn, testData, st2);
			l.verboseRun();
//			l.printResultBasic();
//			l.printResult();


//			simpleTest(knn, data, testData);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
