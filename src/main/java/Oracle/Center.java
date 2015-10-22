package Oracle;

import Utilities.DataSetUtils;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.Random;

/**
 * This algorithm clusters the data sets, and then returns the center-most instance of each cluster.
 *
 * Comments:
 * 		This algorithm seems logical, because I am taking a wide sampling of the entire data set. However,
 * 	The problem becomes that with each iteration while the clusters do change somewhat, they also remain
 * 	very similar, meaning I grab similar data points with each pass of the algorithm.
 *
 * Created by jared on 10/15/15.
 */
public class Center extends IOracle {

	private Random rand;

	private Clusterer clusterer;

	private ArrayList<Instance> set;

	public Center(Dataset pUnlabeledData) {
		this(pUnlabeledData, new KMedoids(pUnlabeledData.size(), -1, new EuclideanDistance()) );
	}

	public Center(Dataset pUnlabeledData, Clusterer pClusterer) {
		super(pUnlabeledData);
		rand = new Random();
		clusterer = pClusterer;
		set = new ArrayList<Instance>();
	}

	@Override
	public Instance getNext() {
		if(set.size() == 0)
			getNextInstances();

		return set.remove(0);
	}

	private void getNextInstances() {
		if(unlabeledData.size() <= 5) {
			for(int i = 0; i < unlabeledData.size(); ++i) {
				set.add(unlabeledData.remove(i));
			}
			return;
		}


		Dataset[] datasets = clusterer.cluster(unlabeledData);

		for(Dataset d : datasets) {
			if(d.size() <= 0)
				continue;

//			int i = rand.nextInt(d.size());

			int i = DataSetUtils.getCenter(d);

			set.add(unlabeledData.remove(unlabeledData.indexOf(d.get(i))));
		}
	}
}
