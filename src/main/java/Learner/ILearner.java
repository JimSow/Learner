package Learner;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Instance;

/**
 * Created by jared on 10/31/15.
 */
public interface ILearner {

	Classifier run();

	void printResult();

	ResultSet getResults();

	int getNumTests();
	double getAccuracy();
	double getRecall();

}
