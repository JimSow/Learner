package DataAccess.OpenML;

import DataAccess.Database.MySQLDataAccessObject;
import Tests.LearnerTests.MySQLTest;
import net.sf.javaml.core.Dataset;

/**
 * Created by jared on 11/19/15.
 */
public class MysqlTest {

	public static void main(String[] args) {
		MySQLDataAccessObject dao = new MySQLDataAccessObject(createAlgorithms());

		int count = 0;

		for(int i = 1; i <= 1130; ++i) {
			String label = (String) dao.getLabel(i);

			if(label != null && !label.isEmpty()) {
				count++;
			}
		}

		System.out.println("There are " + count + " labeled data sets");


		int size = 1;
		int start = 1;
		int offset = 1;

		int[] ids = new int[size];

		for(int i = 0; i < size; ++i) {
			ids[i] = start;

			String label = (String) dao.getLabel(start);

			System.out.println("Checking id = " + start + " Label was: " + label);

			if(label == null || label.isEmpty()) {
				i--;
				System.out.println("Overwriting...");
			}

			start += offset;
		}

		Dataset data = dao.getDataset(ids);

		for(int id : ids) {

			System.out.println("The best Algorithm for " + dao.getName(id) + " (id = " + id + " is " + dao.getLabel(id));

		}

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
