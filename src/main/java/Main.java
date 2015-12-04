import DataAccess.Database.MySQLDataAccessObject;
import Tests.LearnerTests.MySQLTest;
import be.abeel.util.Pair;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.sampling.Sampling;
import net.sf.javaml.tools.weka.WekaClassifier;
import weka.classifiers.bayes.*;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
		try {
			Sampling s = Sampling.SubSampling;
//			Dataset data = ARFFHandler.loadARFF(new File("data/Iris.arff"), 4);

			MySQLDataAccessObject dao = new MySQLDataAccessObject(createAlgorithms());

			Dataset data = dao.getLabeledDataset(createIds(500, 1, 1));


			NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
			nmr.build(data);
			nmr.filter(data);

			Classifier knn = new KNearestNeighbors(5);

//			Classifier knn = new NearestMeanClassifier();

//			for(Instance i : data) {
//				Collection<Double> values = i.values();
//				for(Double d : values) {
//					System.out.printf("%-10f", d);
//				}
//				System.out.println();
//			}



			Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.8));

			knn.buildClassifier(data2.x());

			Map<Object, PerformanceMeasure> map = EvaluateDataset.testDataset(knn, data2.y());

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

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		algorithms.add("weka.RandomTree(%)");
		algorithms.add("weka.SimpleLogistic(%)");
		algorithms.add("weka.DecisionStump(%)");
		algorithms.add("weka.ZeroR(%)");
		algorithms.add("weka.NaiveBayes(%)");
		algorithms.add("weka.KStar(%)");

		return algorithms;
	}
}
