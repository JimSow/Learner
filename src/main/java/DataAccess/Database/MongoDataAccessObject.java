package DataAccess.Database;

import DataAccess.IDataAccessObject;
import com.mongodb.MongoClient;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by jared on 11/10/15.
 */
public class MongoDataAccessObject implements IDataAccessObject {

	final Morphia morphia;

	// create the Datastore connecting to the default port on the local host
	final Datastore datastore;

	MongoDataAccessObject() {
		morphia = new Morphia();
		morphia.mapPackage("main.java.DataAccess.DataBase");

		datastore = morphia.createDatastore(new MongoClient(), "Learner");
		datastore.ensureIndexes();

	}

	public Boolean hasInstance(int id) {
		return datastore.createQuery(MongoDataSet.class)
				.field("_id").equal(id)
				.asList()
				.size() > 0;
	}

	public Instance getInstance(int id) {
		if(!hasInstance(id))
			return null;

		return datastore.createQuery(MongoDataSet.class)
				.field("_id").equal(id)
				.asList()
				.get(0)
				.getAsInstance();
	}

	public Object getLabel(int id) {
		return getInstance(id).classValue();
	}

	public Dataset getDataset(int[] ids) {
		return null;
	}
}
