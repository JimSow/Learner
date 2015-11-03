package JLearn;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.distance.AbstractDistance;

/**
 * Created by jared on 10/31/15.
 */
public class JDistance extends AbstractDistance {


	public double measure(Instance instance0, Instance instance1) {
		int size0 = instance0.noAttributes();
		int size1 = instance1.noAttributes();

		if(size0 != size1)
			throw new IllegalArgumentException("Instances with mismatched sizes");

		double distance = 0;

		for(int i = 0; i < size0; ++i) {
			distance += Math.pow(instance0.value(i) - instance1.value(i), 2);
		}

		return Math.sqrt(distance);
	}

	public boolean compare(double v0, double v1) {
		return v0 < v1;
	}
}
