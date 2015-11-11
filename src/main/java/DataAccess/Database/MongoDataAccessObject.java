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


	public Instance getInstance(int id) {
		return null;
	}

	public Object getLabel(int id) {
		return null;
	}

	public Dataset getDataset() {
		return null;
	}

	public int getCount() {
		return 0;
	}
}
