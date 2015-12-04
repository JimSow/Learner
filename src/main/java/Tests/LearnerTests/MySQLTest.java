package Tests.LearnerTests;

import DataAccess.Database.MySQLDataAccessObject;
import Learner.ILearner;
import Learner.PoolLearning.UncertaintySampling.LeastConfident;
import Learner.ResultSet;
import Learner.StopCondition;
import Oracle.IOracle;
import Oracle.MySQLMetaDataOracle;
import be.abeel.util.Pair;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.sampling.Sampling;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jared on 11/19/15.
 */
public class MySQLTest {
	public static void main(String[] args) {
		MySQLDataAccessObject dao = new MySQLDataAccessObject(createAlgorithms());

		int size = 500;
		int minimumNumberOfTests = 5;

		int ids[] = createIds(size, 1, 1);

		Dataset             data   = dao.getDataset(ids);
		MySQLMetaDataOracle oracle = new MySQLMetaDataOracle(ids, dao);
		StopCondition       st     = new StopCondition(minimumNumberOfTests, size, .85);

		NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
		nmr.build(data);
		nmr.filter(data);

		Sampling s = Sampling.SubSampling;

		Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.7), 1);

		data = data2.x();
		Dataset testData = data2.y();


		for(Instance i : testData) {
			Object label = oracle.getLabel(i);

			System.out.println("Label: " + label);

			i.setClassValue(label);
		}

		data2 = s.sample(data2.y(), (int) (data2.y().size() * 0.67), 1);

		testData = data2.x();
		Dataset assurance = data2.y();


		for(Instance i : testData) {
			i.setClassValue(oracle.getLabel(i));
		}

		Classifier knn = new KNearestNeighbors(5);

		System.out.println("Data size:" + data.size() + " Test data size: " + testData.size()
								   + " Assurance: " + assurance.size());

		ILearner l = new LeastConfident(oracle, knn, data, testData, st, minimumNumberOfTests);

		Classifier classifier = l.run();
		ResultSet rs = l.getResults();

		doubleCheck(classifier, assurance);

		rs.printResults();
	}

	public static int[] createIds(int size, int start, int offset) {
		if(size < 1)
			size = 1;
		if(size > 500)
			size = 500;
		if(start < 1 || start > 500)
			start = 1;
		if(offset < 1 || offset * size > 500)
			offset = 1;

		MySQLDataAccessObject dao = new MySQLDataAccessObject(createAlgorithms());

		int[] ids = new int[size];

		for(int i = 0; i < size; ++i) {
			ids[i] = start;

			String label = (String) dao.getLabel(start);
			if(label == null || label.isEmpty()) {
				i--;
			}

			start += offset;
		}

		return ids;
	}

	public static List<String> createAlgorithms() {
		List<String> algorithms = new ArrayList<String>();

		algorithms.add("weka.IBk(%)");
		algorithms.add("weka.J48(%)");
		algorithms.add("weka.JRip(%)");
		algorithms.add("weka.RandomTree(%)");
		algorithms.add("weka.SimpleLogistic(%)");
		algorithms.add("weka.DecisionStump(%)");
		algorithms.add("weka.ZeroR(%)");
		algorithms.add("weka.NaiveBayes(%)");
		algorithms.add("weka.KStar(%)");
		algorithms.add("weka.ADTree(%)");
		algorithms.add("weka.Bagging(%)");
		algorithms.add("weka.K2(%)");
		algorithms.add("weka.LADTree(%)");
		algorithms.add("weka.LBR(%)");
		algorithms.add("weka.KernelLogisticRegression_PolyKernel(%)");
		algorithms.add("weka.KernelLogisticRegression_RBFKernel(%)");

		return algorithms;
	}

	public static void doubleCheck(Classifier c, Dataset test) {

		double correct = 0;
		double incorrect = 0;
		double size = test.size();

		for(Instance i : test) {

			Object label = c.classify(i);
			Object expectedLabel = i.classValue();

			System.out.println("Predicted: " + label + " Acctual: " + expectedLabel);

			if(label.equals(expectedLabel))
				++correct;
			else
				++incorrect;
		}

		System.out.println("Correct %  : " + correct   / size);
		System.out.println("InCorrect %: " + incorrect / size);
	}

}
