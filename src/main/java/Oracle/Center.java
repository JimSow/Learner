package Oracle;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jared on 10/15/15.
 */
public class Center extends IOracle {

	private Random rand;

	private Clusterer clusterer;

	private ArrayList<Instance> set;

	public Center(Dataset pUnlabeledData) {
		this(pUnlabeledData, new KMedoids());
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

			int i = rand.nextInt(d.size());

			set.add(unlabeledData.remove(unlabeledData.indexOf(d.get(i))));
		}
	}
}
