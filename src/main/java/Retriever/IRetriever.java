package Retriever;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;

import java.util.List;

/**
 * Created by jared on 10/15/15.
 */
public abstract class IRetriever {
	protected Dataset unlabeledData;
	protected Dataset labeledData;

	protected IRetriever(Dataset pUnlabeledData) {
		unlabeledData = pUnlabeledData;
		labeledData = new DefaultDataset();
	}

	public Instance getNext() { return get(1).get(0); }
	public abstract List<Instance> get(int amount);
}
