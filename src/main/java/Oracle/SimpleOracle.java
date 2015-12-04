package Oracle;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.HashMap;

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
