package Oracle;

import DataAccess.Database.MySQLDataAccessObject;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by jared on 11/26/15.
 */
public class MetaDataOracle extends IOracle {

	List<Integer>         ids;
	MySQLDataAccessObject dao;


	public MetaDataOracle(int[] pIds, Dataset pData) {

		super(pData);

		ids = new LinkedList<Integer>();

		for(int i : pIds){
			ids.add(i);
		}

		dao = new MySQLDataAccessObject();
	}

	@Override
	public Object getLabel(int index) {
		Integer id = ids.remove(index);

		return dao.getLabel(id);
	}
}
