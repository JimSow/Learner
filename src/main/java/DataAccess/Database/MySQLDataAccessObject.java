package DataAccess.Database;

import DataAccess.IDataAccessObject;
import javassist.NotFoundException;
import net.sf.javaml.core.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jared on 11/19/15.
 */
public class MySQLDataAccessObject implements IDataAccessObject {
	final String url = "jdbc:mysql://localhost:8889/";
	final String dbName = "openML";
	final String driver = "com.mysql.jdbc.Driver";
	final String userName = "root";
	final String password = "cocobear1";
	Connection conn;

	private List<String> qualities;
	private List<String> algorithms;

	public MySQLDataAccessObject() {
		connect();
		qualities = getQualities();
	}

	public MySQLDataAccessObject(List<String> pAlgorithms) {
		connect();
		qualities = getQualities();
		algorithms = pAlgorithms;
	}

	public void setAlgorithms(List<String> pAlgorithms) { algorithms = pAlgorithms; }

	public Instance getInstance(int id) {

		SparseInstance instance = new SparseInstance();

		Statement st = null;
		try {
			st = conn.createStatement();

			String query;
			ResultSet res;

			int i = 0;
			for(String quality : qualities) {
				try {
					query = "select * from data_quality as q" +
							" where q.data=" + id +
							" and q.quality='" + quality + "'";

					res = st.executeQuery(query);

					if(res.next()) {
						instance.put(i, res.getDouble("value"));
						if(res.wasNull())
							instance.put(i,0d);

					}
					else
						instance.put(i,0d);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				++i;
			}




		} catch (SQLException e) {
			e.printStackTrace();
		}
		return instance;
	}

	public String getName(int id) {
		String name = null;

		try {
			Statement st = conn.createStatement();

			String query = "select * from dataset"
					+ " where did = " + id;

			ResultSet res = st.executeQuery(query);

			res.next();
			name = res.getString("name");
		} catch (SQLException e) {
		}

		return name;
	}

	public List<String> getNames(int[] ids) {
		List<String> labels = new ArrayList<String>(ids.length);

		for(int id : ids) {
			labels.add(getName(id));
		}

		return labels;
	}

	public Object getLabel(int id) {

		String best = "";
		double highest = -1;

		for(String algorithm : algorithms) {
			try {
				double newValue = getValueForAlgorithm(id, algorithm);

				if(newValue > highest) {
					best = algorithm;
					highest = newValue;
				}

			} catch (NotFoundException e) {
				System.out.println("Couldn't find algorithm " + algorithm + " for id = " + id);
				continue; //TODO change this to run the algorithm
			}
		}

		return best;
	}

	public List<Object> getLabels(int[] ids) {
		List<Object> labels = new ArrayList<Object>(ids.length);

		for(int id : ids) {
			labels.add(getLabel(id));
		}

		return labels;
	}

	public Dataset getDataset(int[] ids) {
		Dataset data = new DefaultDataset();

		for(int id : ids) {
			Instance instance = getInstance(id);
			data.add(instance);
		}

		return data;
	}

	public Dataset getLabeledDataset(int[] ids) {
		Dataset data = new DefaultDataset();

		for(int id : ids) {
			Instance instance = getInstance(id);
			instance.setClassValue(getLabel(id));
			data.add(instance);
		}

		return data;
	}

	private double getValueForAlgorithm(int id, String Algorithm) throws NotFoundException {
		String query =
				"SELECT i.fullName, avg(e.value) as value\n" +
						"FROM algorithm_setup l, evaluation e, run r, dataset d, implementation i, task_inputs ti\n" +
						"WHERE r.setup = l.sid\n" +
						"AND l.isDefault = 'true'\n" +
						"AND r.task_id = ti.task_id\n" +
						"AND ti.input='source_data'\n" +
						"AND ti.value=d.did\n" +
						"AND l.implementation_id = i.id\n" +
						"AND d.isOriginal='true'\n" +
						"AND e.source=r.rid\n" +
						"AND e.function='predictive_accuracy'\n" +
						"AND d.did = " + id + "\n" +
						"and i.fullname LIKE '" + Algorithm + "'\n" +
						"group by i.fullname\n" +
						"ORDER BY e.value DESC;";

		Statement st = null;
		try {
			st = conn.createStatement();

			ResultSet res;

			res = st.executeQuery(query);

			double i = 0;
			double value = 0;

			while(res.next()) {
				value += res.getDouble("value");

				++i;
			}

			return value / i;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		throw new NotFoundException("The result for " + Algorithm + " on data set " + id + " could not be found.");
	}

	private List<String> getQualities() {
		List<String> qualities = new ArrayList<String>(100);
		Statement st = null;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from quality");

			while (res.next()) {
				qualities.add(res.getString("name"));
			}

		} catch (SQLException e) {

		}
		return qualities;
	}

	private void connect() {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
