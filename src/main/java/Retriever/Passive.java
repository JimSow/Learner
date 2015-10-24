package Retriever;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jared on 10/15/15.
 */
public class Passive extends IRetriever {

	private Random rand;

	public Passive(Dataset pUnlabeledData) {
		super(pUnlabeledData);
		rand = new Random();
	}

	public Passive(Dataset pUnlabeledData, int seed) {
		super(pUnlabeledData);
		rand = new Random(seed);
	}

	@Override
	public List<Instance> get(int amount) {
		List<Instance> instances = new ArrayList<Instance>(amount);

		while (amount > 0) {
			instances.add(unlabeledData.remove(rand.nextInt(unlabeledData.size())));
			--amount;
		}

		return instances;
	}
}
