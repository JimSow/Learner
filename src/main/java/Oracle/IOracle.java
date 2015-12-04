package Oracle;

import net.sf.javaml.core.Instance;

/**
 * Created by jared on 10/24/15.
 */
public interface IOracle {
	Object getLabel(Instance instance);
}
