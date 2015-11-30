package DataAccess;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

/**
 * Created by jared on 11/7/15.
 */
public interface IDataAccessObject {
	Instance getInstance(int id);
	Object   getLabel   (int id);
	Dataset getDataset  (int[] ids);
//	int     getCount  ();
}
