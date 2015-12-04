package Learner.HypothesisSpaceSearch.StreamLearning;

import Learner.StopCondition;
import Oracle.IOracle;
import net.sf.javaml.classification.*;
import net.sf.javaml.classification.bayes.NaiveBayesClassifier;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.exception.TrainingRequiredException;
import net.sf.javaml.tools.weka.WekaClassifier;

import java.util.*;

/**
 * Created by jared on 11/2/15.
 */
public class QueryByCommitee extends AStreamLearner {

	List<Classifier> commitee;
	int commiteeSize;
	double threshold;

	public QueryByCommitee(Classifier pClassifier, IOracle pOracle, Dataset pTestData, StopCondition pTarget
			, double pThreshold) {
		super(pClassifier, pOracle, pTestData, pTarget);

		commitee = buildSimpleComitee();
		threshold = pThreshold;
	}

	@Override
	protected Boolean runOne(Instance pNewInstance) {

		try{
			trainCommitee(labeledData);
		} catch(TrainingRequiredException ex) {
			// If there is no labeled data, always run the test.
			return true;
		}

		return (getCertainty(pNewInstance) < threshold);
	}

	private double getCertainty(Instance instance) throws TrainingRequiredException {
		Map<Object, Integer> agreement = new HashMap<Object, Integer>(commiteeSize);
		double maxCertainty = 0;

		for(Classifier classifier : commitee) {
			Object label = classifier.classify(instance);

			if(!agreement.containsKey(label))
				agreement.put(label,1);
			else
				agreement.put(label, agreement.get(label) + 1);
		}

		Iterator it = agreement.entrySet().iterator();
		double certainty;

		while (it.hasNext()) {
			Map.Entry<Object, Double> pair = (Map.Entry<Object, Double>)it.next();

			certainty = pair.getValue() / (double)commiteeSize;

			maxCertainty = (certainty > maxCertainty ? certainty : maxCertainty);

			it.remove();
		}

		return maxCertainty;
	}

	private void trainCommitee(Dataset data) {
		for(Classifier classifier : commitee) {
			classifier.buildClassifier(data);
		}
	}

	private List<Classifier> buildSimpleComitee(){ return buildSimpleComitee(5); }
	private List<Classifier> buildSimpleComitee(int numMembers) {
		int size = (numMembers > 10 ? 11 : numMembers);
		int forestSize = numMembers - 10;

		List<Classifier> newCommitee = new ArrayList<Classifier>(size);
		commiteeSize = size;

		if(forestSize > 0) {
			newCommitee.add(new RandomForest(forestSize));
		}

		switch(numMembers){
			case 10:
				newCommitee.add(new WekaClassifier(new weka.classifiers.meta.AdaBoostM1()));
			case 9:
				newCommitee.add(new WekaClassifier(new weka.classifiers.trees.J48()));
			case 8:
				newCommitee.add(new MeanFeatureVotingClassifier());
			case 7:
				newCommitee.add(new WekaClassifier(new weka.classifiers.functions.MultilayerPerceptron()));
			case 6:
				newCommitee.add(new NearestMeanClassifier());
			case 5:
				newCommitee.add(new NaiveBayesClassifier(true, true, false));
			case 4:
				newCommitee.add(new KDtreeKNN(3));
			default:
				newCommitee.add(new WekaClassifier(new weka.classifiers.trees.Id3()));
				newCommitee.add(new WekaClassifier(new weka.classifiers.functions.LibSVM()));
				newCommitee.add(new KNearestNeighbors(3));
		}

		return newCommitee;
	}
}
