package Retriever;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jared on 10/16/15.
 */
public class ReverseCenter extends IRetriever {
	private DistanceMeasure dm;

	private Instance next;

	public ReverseCenter(Dataset pUnlabeledData) { this(pUnlabeledData, new EuclideanDistance()); }

	public ReverseCenter(Dataset pUnlabeledData, DistanceMeasure pDm) {
		super(pUnlabeledData);

		dm = pDm;

		int first = getFirst();

		next = unlabeledData.remove(first);
	}

	@Override
	public List<Instance> get(int amount) {
		List<Instance> instances = new ArrayList<Instance>(amount);

		while (amount > 0) {
			instances.add(next);

			next = getFurthest();

			if(next == null)
				break;

			unlabeledData.remove(next);
			labeledData.add(next);

			--amount;
		}
		if(instances.size() <= 0)
			return null;
		return instances;
	}

	private Instance getFurthest() {
		if(unlabeledData.size() <= 0)
			return null;

		double furthestDist = 0;
		Instance furthest = unlabeledData.get(0);

		for(Instance uLabeled : unlabeledData) {
			double curDist = 0;
			for(Instance labeled : labeledData) {
				curDist += dm.measure(uLabeled, labeled);
			}
			curDist /= labeledData.size(); // Is this necessary? Technically we care about scale, not exact numbers

			if(curDist > furthestDist) {
				furthestDist = curDist;
				furthest = uLabeled;
			}
		}
		return furthest;
	}

	private int getFirst() {
		return new Random().nextInt(unlabeledData.size());
	}


}
