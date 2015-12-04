package Oracle;

import DataAccess.Database.MySQLDataAccessObject;
import net.sf.javaml.core.Instance;

import java.util.HashMap;

/**
 * Created by jared on 11/26/15.
 */
public class MySQLMetaDataOracle implements IOracle {

	HashMap<Instance, Object> instances;

	public MySQLMetaDataOracle(int[] pIds, MySQLDataAccessObject dao){
		instances = new HashMap<Instance, Object>();

		int size = pIds.length;

		for(int i = 0; i < size; ++i) {
			int id = pIds[i];
			instances.put(dao.getInstance(id), dao.getLabel(id));
		}

	}

	public Object getLabel(Instance instance) {
		return instance.get(instance);
	}
}
