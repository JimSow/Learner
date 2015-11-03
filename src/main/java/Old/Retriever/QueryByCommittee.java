package Old.Retriever;

import net.sf.javaml.classification.*;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jared on 10/24/15.
 */
public class QueryByCommittee extends IRetriever {
	private List<Classifier> committee;

	protected QueryByCommittee(Dataset pUnlabeledData) {
		super(pUnlabeledData);
		committee = new ArrayList<Classifier>();
		committee.add(new KNearestNeighbors(5));
		committee.add(new KDtreeKNN(5));
		committee.add(new MeanFeatureVotingClassifier());
		committee.add(new NearestMeanClassifier());
		committee.add(new NaiveBayesClassifier(true, true, false));
	}

	protected QueryByCommittee(Dataset pUnlabeledData, List<Classifier> pCommittee) {
		super(pUnlabeledData);
		committee = pCommittee;
	}

	@Override
	public List<Instance> get(int amount) {
		return null;
	}
}
