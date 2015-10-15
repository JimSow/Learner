package Oracle;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 * Created by jared on 10/15/15.
 */
public abstract class IOracle {
	protected Dataset unlabeledData;

	protected IOracle(Dataset pUnlabeledData) {
		unlabeledData = pUnlabeledData;
	}

	public abstract Instance getNext();
}
