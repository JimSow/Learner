package Oracle;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

/**
 * Created by jared on 10/24/15.
 */
public class SimpleOracle extends IOracle {

	private Dataset data;

	public SimpleOracle(Dataset pData) {
		data = pData;
	}

	@Override
	public Object getLabel(Instance unlabeled) {
		return data.kNearest(1, unlabeled, new EuclideanDistance());
	}
}
