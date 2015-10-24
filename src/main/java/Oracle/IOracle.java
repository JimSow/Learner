package Oracle;

import net.sf.javaml.core.Instance;

/**
 * Created by jared on 10/24/15.
 */
public abstract class IOracle {
	public abstract Object getLabel(Instance unlabeled);
}
