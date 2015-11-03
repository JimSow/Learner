package Oracle;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

/**
 * Created by jared on 10/24/15.
 */
public class SimpleOracle extends IOracle {

	private Dataset data;

	private Classifier c;

	private DistanceMeasure dm;

	public SimpleOracle(Dataset pData) {
		data = pData;
		c = new KNearestNeighbors(1);
		c.buildClassifier(data);
		dm = new EuclideanDistance();
	}

	@Override
	public Object getLabel(Instance unlabeled) {

		if(data.size() <= 0){
			return null;
		}

		double distance = Double.MAX_VALUE;
		Instance next = null;

		for(Instance item : data) {
			double temp = dm.measure(unlabeled, item);

			if(temp < distance) {
				if(temp == 0){
					return item.classValue();
				}
				distance = temp;
				next = item;
			}
		}
		return next.classValue();
	}
}
