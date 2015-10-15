import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;

/**
 * Created by jared on 10/15/15.
 */
public class Passive {

	private double targetAccuracy;

	private Classifier test;

	private Dataset unlabeledData;
	private Dataset labeledData;

	Passive(Dataset pUnlabeledData, Classifier pTest, double pTargetAccuracy) {

	}
}
