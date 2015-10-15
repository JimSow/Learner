import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.sampling.Sampling;
import net.sf.javaml.tools.data.ARFFHandler;

import be.abeel.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
		try {
			Sampling s = Sampling.SubSampling;
			Dataset data = ARFFHandler.loadARFF(new File("data/diabetes.arff"), 8);

			NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
			nmr.build(data);
			nmr.filter(data);

//			for(Instance i : data) {
//				Collection<Double> values = i.values();
//				for(Double d : values) {
//					System.out.printf("%-10f", d);
//				}
//				System.out.println();
//			}

			Classifier knn = new KNearestNeighbors(5);

			Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.8));

			knn.buildClassifier(data2.x());

			Map<Object, PerformanceMeasure> map = EvaluateDataset.testDataset(knn, data2.y());

			for(Object o:map.keySet())
				System.out.println(o + ": " + map.get(o).getAccuracy());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
