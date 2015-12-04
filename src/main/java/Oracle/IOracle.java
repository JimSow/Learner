package Oracle;

import net.sf.javaml.core.Instance;

/**
 * Created by jared on 10/24/15.
 */
public abstract class IOracle {

	Dataset data;

	public IOracle(Dataset pData) {
		if(pData == null || pData.size() < 1) {
			throw new InvalidParameterException("Cant use an empty data set to build the Oracle");
		}
		data = pData.copy();
	}

	public abstract Object getLabel(int index);
}
