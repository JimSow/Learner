package Oracle;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.HashMap;

/**
 * Created by jared on 10/24/15.
 */
public class SimpleOracle implements IOracle {

	HashMap<Instance, Object> instances;


	public SimpleOracle(Dataset pData) {
		instances = new HashMap<Instance, Object>();

		for (Instance instance : pData) {
			instances.put(instance, instance.classValue());
		}
	}

	public Object getLabel(Instance instance) {
		return instances.get(instance);
	}
}
