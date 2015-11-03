package JLearn;

import com.sun.tools.javac.util.Pair;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.exception.TrainingRequiredException;
import net.sf.javaml.distance.DistanceMeasure;

import java.util.*;

/**
 * Created by jared on 10/31/15.
 */
public class KNN implements Classifier {

	private Dataset data;

	private DistanceMeasure distance;

	private int neighbors;

	public KNN(int pNeighbors, DistanceMeasure pDistance) {
		neighbors = pNeighbors;
		distance = pDistance;
	}

	public void buildClassifier(Dataset dataset) {
		data = dataset;
	}

	public Object classify(Instance instance) {
		HashMap closest = new HashMap();
		double max = distance.getMaxValue();
		Iterator i$ = data.iterator();

		while(i$.hasNext()) {
			Instance tmp = (Instance)i$.next();
			double d = distance.measure(instance, tmp);
			if(distance.compare(d, max) && !instance.equals(tmp)) {
				closest.put(tmp, Double.valueOf(d));
				if(closest.size() > neighbors) {
					max = removeFarthest(closest, distance);
				}
			}
		}

		return closest.keySet();
	}

	private double removeFarthest(Map<Instance, Double> vector, DistanceMeasure dm) {
		Instance tmp = null;
		double max = dm.getMinValue();
		Iterator i$ = vector.keySet().iterator();

		while(i$.hasNext()) {
			Instance inst = (Instance)i$.next();
			double d = ((Double)vector.get(inst)).doubleValue();
			if(dm.compare(max, d)) {
				max = d;
				tmp = inst;
			}
		}

		vector.remove(tmp);
		return max;
	}

//	public Object classify(Instance instance) {
//		Map<Object, Double> dist = classDistribution(instance);
//
//		Object label = null;
//		double max = 0;
//
//		for(Object key : dist.keySet()) {
//			double val = dist.get(key);
//
//			if(val > max) {
//				max = val;
//				label = key;
//			}
//		}
//
//		return label;
//	}

//	public Map<Object, Double> classDistribution(Instance instance) {
//		HashMap closest = new HashMap();
//		double max = distance.getMaxValue();
//		Iterator i$ = this.iterator();
//
//		while(i$.hasNext()) {
//			Instance tmp = (Instance)i$.next();
//			double d = distance.measure(inst, tmp);
//			if(distance.compare(d, max) && !inst.equals(tmp)) {
//				closest.put(tmp, Double.valueOf(d));
//				if(closest.size() > k) {
//					max = this.removeFarthest(closest, distance);
//				}
//			}
//		}
//
//		return closest.keySet();
//	}

	public Map<Object, Double> classDistribution(Instance instance) {
		if(data == null) {
			throw new TrainingRequiredException();
		} else {
			Set neighborSet = data.kNearest(neighbors, instance, distance);
			HashMap out = new HashMap();
			Iterator min = data.classes().iterator();

			while(min.hasNext()) {
				Object i = min.next();
				out.put(i, Double.valueOf(0.0D));
			}

			min = neighborSet.iterator();

			while(min.hasNext()) {
				Instance i1 = (Instance)min.next();
				out.put(i1.classValue(), Double.valueOf(((Double)out.get(i1.classValue())).doubleValue() + 1.0D));
			}

			double min1 = (double)neighbors;
			double max = 0.0D;
			Iterator i$ = out.keySet().iterator();

			Object key;
			while(i$.hasNext()) {
				key = i$.next();
				double val = ((Double)out.get(key)).doubleValue();
				if(val > max) {
					max = val;
				}

				if(val < min1) {
					min1 = val;
				}
			}

			if(max != min1) {
				i$ = out.keySet().iterator();

				while(i$.hasNext()) {
					key = i$.next();
					out.put(key, Double.valueOf((((Double)out.get(key)).doubleValue() - min1) / (max - min1)));
				}
			}

			return out;
		}
	}

//	public Map<Object, Double> classDistribution(Instance instance) {
//		ArrayList<Pair<Instance, Double>> neighborList = new ArrayList<Pair<Instance, Double>>(neighbors);
//
//		for(Instance inst : data) {
//			for(int i = 0; i < neighbors; ++neighbors) {
//				double dist = distance.measure(instance, inst);
//				try {
//					if (dist < neighborList.get(i).snd) {
//						neighborList.set(i, new Pair<Instance, Double>(inst, dist));
//						break;
//					}
//				} catch (IndexOutOfBoundsException e) {
//					neighborList.add(i, new Pair<Instance, Double>(inst, dist));
//					break;
//				}
//			}
//		}
//
//		Map<Object, Double> classDist = new HashMap<Object, Double>();
//
//		for(Object label : data.classes()) {
//			classDist.put(label, getDistributionForLabel(neighborList, label));
//		}
//
//		return classDist;
//	}

	private double getDistributionForLabel(ArrayList<Pair<Instance, Double>> neighborList, Object label) {
		double count = 0;

		for(int i = 0; i < neighbors; ++i) {
			if(neighborList.get(i).fst.classValue() == label){
				count++;
			}
		}

		return count / neighbors;
	}
}
