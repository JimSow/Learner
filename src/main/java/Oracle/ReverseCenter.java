package Oracle;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.Random;

/**
 * Created by jared on 10/16/15.
 */
public class ReverseCenter extends IOracle {
	private DistanceMeasure dm;

	private Dataset labeledData;

	private Instance next;

	public ReverseCenter(Dataset pUnlabeledData) { this(pUnlabeledData, new EuclideanDistance()); }

	public ReverseCenter(Dataset pUnlabeledData, DistanceMeasure pDm) {
		super(pUnlabeledData);

		dm = pDm;

		int first = getFirst();

		next = unlabeledData.remove(first);

		labeledData = new DefaultDataset();
	}

	@Override
	public Instance getNext() {
		Instance temp = next;

		next = getFurthest();

		if(next == null)
			return null;

		unlabeledData.remove(next);
		labeledData.add(next);

		return temp;
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
