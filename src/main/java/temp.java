import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.ARFFHandler;
import net.sf.javaml.tools.weka.WekaClassifier;

import java.io.File;
import java.util.Map;

/**
 * Created by jared on 12/3/15.
 */
public class temp {

	public static void main(String[] args) {
//		String classifierName = "weka.classifiers.rules.ZeroR";
//		String classifierName = "weka.classifiers.rules.OneR -- -B 6";
		String classifierName = "weka.classifiers.trees.J48 -- -C 0.25 -M 2";
//		String classifierName = "weka.classifiers.trees.DecisionStump";
//		String classifierName = "weka.classifiers.lazy.KStar -- -B 20 -M a";
//		String classifierName = "weka.classifiers.trees.J48 -- -C 0.25 -M 2";
//		String classifierName = "weka.classifiers.trees.J48 -- -C 0.25 -M 2";
//		String classifierName = "weka.classifiers.trees.J48 -- -C 0.25 -M 2";

		weka.classifiers.Classifier wekaClassifier;

		String[] options = new String[0];
		try {
			String dataFile = "data/adult.arff"   ; int classLoc = 14; // 30,000+ instances

			Dataset data = ARFFHandler.loadARFF(new File(dataFile), classLoc);

			options = weka.core.Utils.splitOptions(classifierName);

			String[] split = classifierName.split("--");

			classifierName = options[0];

//			options[0] = "";
//			options[1] = "";


			for(int i = 0; i < options.length; ++i) {

				if(options[i].equals("--") ) {
					options[i] = "";
					break;
				}

				System.out.println("Replacing " + options[i] + " with \"\"");
				options[i] = "";
			}

			for(String option : options) {
				System.out.println(option);
			}

			System.out.println("");
			System.out.println("Building Classifier");

			wekaClassifier = weka.classifiers.Classifier.forName(classifierName, options);

			Classifier classifier = new WekaClassifier(wekaClassifier);

			classifier.buildClassifier(data);

			Map<Object, PerformanceMeasure> results = EvaluateDataset.testDataset(classifier, data);

			for (Object o : results.keySet()) {
				System.out.println(o + " Accuracy: " + results.get(o).getAccuracy());
				System.out.println(o + " BCR: " + results.get(o).getBCR());
				System.out.println(o + " Correlation: " + results.get(o).getCorrelation());
				System.out.println(o + " Correlation Coefficient: " + results.get(o).getCorrelationCoefficient());
				System.out.println(o + " Cost: " + results.get(o).getCost());
				System.out.println(o + " Error rate: " + results.get(o).getErrorRate());
				System.out.println(o + " F Measure: " + results.get(o).getFMeasure());
				System.out.println(o + " FN Rate: " + results.get(o).getFNRate());
				System.out.println(o + " FP Rate: " + results.get(o).getFPRate());
				System.out.println(o + " Precision: " + results.get(o).getPrecision());
				System.out.println(o + " Q9: " + results.get(o).getQ9());
				System.out.println(o + " Recall: " + results.get(o).getRecall());
				System.out.println(o + " TN Rate: " + results.get(o).getTNRate());
				System.out.println(o + " Total: " + results.get(o).getTotal());
				System.out.println(o + " TP Rate: " + results.get(o).getTPRate());
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
