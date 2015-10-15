package Oracle;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.Random;

/**
 * Created by jared on 10/15/15.
 */
public class Passive extends IOracle {

	private Random rand;

	private Passive(Dataset pUnlabeledData) {
		super(pUnlabeledData);
		rand = new Random();
	}

	@Override
	public Instance getNext() {
		return unlabeledData.remove(rand.nextInt(unlabeledData.size()));
	}
}
