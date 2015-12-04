package DataAccess.OpenML;

import Learner.ResultSet;
import be.abeel.util.Pair;
import com.cedarsoftware.util.io.JsonWriter;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.sampling.Sampling;
import net.sf.javaml.tools.data.ARFFHandler;
import org.json.JSONObject;
import org.openml.apiconnector.io.OpenmlConnector;
import org.openml.apiconnector.xml.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

/**
 * Created by jared on 11/14/15.
 */
public class test {
	public static void main(String[] args) {
//		Authenticate auth = new Authenticate();

		String hash = "54552c2da5b4c277ef2539a5d03b61bd";

		OpenmlConnector client = new OpenmlConnector(hash);

		try {
			JSONObject jsonObject = client.freeQuery("SELECT name FROM dataset");

			System.out.println(JsonWriter.formatJson(jsonObject.toString()));

			File file = new File("json.txt");
			file.createNewFile();

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));

			bw.write(JsonWriter.formatJson(jsonObject.toString()));

			bw.flush();
			bw.close();

//			Iterator keys = jsonObject.keys();
//
//			while(keys.hasNext()) {
//				String next = (String) keys.next();
//
//				System.out.println(jsonObject.get(next).toString());
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println();


		try {
			Flow flow = client.flowGet(1);

			Run run = client.runGet(332343);

			EvaluationScore[] outputEvaluation = run.getOutputEvaluation();

			for(int i = 0; i < outputEvaluation.length; ++i){
				System.out.println(outputEvaluation[i].getFunction() + " : " + outputEvaluation[i].getValue());
			}

			int flow_id = run.getFlow_id();

			System.in.read();

		} catch (Exception e) {
			e.printStackTrace();
		}


		Dataset data;

		for (int i = 1; i < 10; ++i) {
			try {
				DataSetDescription dataDescription = client.dataGet(i);

				System.out.println("ID: " + i);
				System.out.println("Name: " + dataDescription.getName());
				System.out.println("Url: " + dataDescription.getUrl());
				System.out.println("Language: " + dataDescription.getLanguage());
				System.out.println("Format: " + dataDescription.getFormat());

				File dataFile = dataDescription.getDataset(hash);
				data = ARFFHandler.loadARFF(dataFile);
				data = ARFFHandler.loadARFF(dataFile, data.noAttributes() - 1);

				System.out.println("Num Attributes: " + data.noAttributes());



				NormalizeMidrange nmr = new NormalizeMidrange(0, 2);
				nmr.build(data);
				nmr.filter(data);

				Sampling s = Sampling.SubSampling;

				Pair<Dataset, Dataset> data2 = s.sample(data, (int) (data.size() * 0.7), 1);

				data = data2.x();
				Dataset testData = data2.y();

				Classifier knn = new KNearestNeighbors(5);

				knn.buildClassifier(data);

				Map<Object, PerformanceMeasure> results = EvaluateDataset.testDataset(knn, testData);

				ResultSet latestResults = new ResultSet(results, 1);

				System.out.println("Accuracy: " + latestResults.getAccuracy());

//				System.out.println("Description: " + data.getDescription());
//				System.out.println("Default Target Attribute: " + data.getDefault_target_attribute());

//				System.out.println("Tags:");
//				String[] tags = data.getTag();
//				for(String tag : tags){
//					System.out.println("\t" + tag);
//				}

						System.out.println();
				System.out.println("-----------------------------------");
				System.out.println();


			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
