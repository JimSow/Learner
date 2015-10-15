package Learner;

import Oracle.IOracle;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

/**
 * Created by jared on 10/15/15.
 */
public class Learner implements Runnable {

	private Classifier test;

	private Dataset labeledData;

	private Success success;

	private IOracle oracle;

	Learner(Dataset pUnlabeledData, Classifier pTest, Success pTarget) {

	}

	public void run() {

	}

	private boolean checkSuccess() {
		return false;
	}
}
