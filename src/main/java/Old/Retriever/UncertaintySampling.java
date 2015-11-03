package Old.Retriever;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jared on 10/31/15.
 */
public class UncertaintySampling extends IRetriever {

	Classifier classifier;

	protected UncertaintySampling(Dataset pUnlabeledData, Classifier pClassifier) {
		super(pUnlabeledData);

		classifier = pClassifier;
	}

	@Override
	public List<Instance> get(int amount) {

		List<Instance> InstanceList = new ArrayList<Instance>(amount);

		for(int i = 0; i < amount; ++i) {
			classifier.buildClassifier(labeledData);

			Instance next = unlabeledData.remove(getLeastCertain());

			labeledData.add(next);

			InstanceList.add(next);
		}
		return InstanceList;
	}

	private int getLeastCertain() {
		return 0;
	}
}
