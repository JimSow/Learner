package Utilities;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.DatasetTools;

/**
 * Created by jared on 10/16/15.
 */
public class DataSetUtils {

	public static int getCenter(Dataset set) {
		return getCenter(set, new EuclideanDistance());
	}

	public static int getCenter(Dataset set, DistanceMeasure dm) {
		Instance trueCenter = DatasetTools.average(set);
		double best = Double.MAX_VALUE;
		int center = 0;
		int index = 0;

		for(Instance i : set) {
			double distance = dm.measure(trueCenter, i);

			if(best >= distance) {
				best = distance;
				center = index;
			}
			index++;
		}

		return center;
	}

}
