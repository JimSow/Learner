package Oracle;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

import java.security.InvalidParameterException;

/**
 * Created by jared on 10/24/15.
 */
public class SimpleOracle extends IOracle {

	private Classifier c;

	private DistanceMeasure dm;

	public SimpleOracle(Dataset pData) {
		super(pData);
	}

	@Override
	public Object getLabel(int index) {

		if(data.size() <= 0){ return null; }
		return data.classValue(index);
	}
}
