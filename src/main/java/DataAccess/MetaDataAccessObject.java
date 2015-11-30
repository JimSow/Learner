package DataAccess;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 * Created by jared on 11/7/15.
 */
public class MetaDataAccessObject implements IDataAccessObject {
	public Instance getInstance(int id) {
		return null;
	}

	public Object getLabel(int id) {
		return null;
	}

	public Dataset getDataset(int[] ids) {
		return null;
	}

	public Dataset getDataset() {
		return null;
	}

	public int getCount() {
		return 0;
	}
}
