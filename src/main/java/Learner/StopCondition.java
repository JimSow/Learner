package Learner;

import net.sf.javaml.classification.evaluation.PerformanceMeasure;

import java.util.Map;

/**
 * Created by jared on 10/15/15.
 */
public class StopCondition {
	int minNumTests;
	int maxNumTests;

	double targetAccuracy;
	double targetRecall;

	// Run pMaxNumTests regardless of anything else
	public StopCondition(int pNumTestsToRun) {
		this(0, pNumTestsToRun, 1, 1);
	}

	//Run tests until you reach pTargetAccuracy regardless of anything except pMaxNumTests
	public StopCondition(int pMinNumTests, int pMaxNumTests, double pTargetAccuracy) {
		this(pMinNumTests, pMaxNumTests, pTargetAccuracy, 0);
	}

	//Run up to pMaxNumTests until all other conditions are met
	public StopCondition(int pMinNumTests, int pMaxNumTests, double pTargetAccuracy, double pTargetRecall){
		minNumTests    = pMinNumTests;
		maxNumTests    = pMaxNumTests;
		targetAccuracy = pTargetAccuracy;
		targetRecall   = pTargetRecall;
	}


	public boolean Stop(Map<Object, PerformanceMeasure> pm, int pNumTests) {
		if(pNumTests < minNumTests)
			return false;
		if(pNumTests >= maxNumTests)
			return true;

		double accuracy = 0;
		double recall   = 0;
		int i = 0;

		for(Object o : pm.keySet()) {
			accuracy += pm.get(o).getAccuracy();
			recall   += pm.get(o).getRecall();
			i++;
		}

		accuracy /= i;
		recall   /= i;

		if(accuracy >= targetAccuracy && recall >= targetRecall)
			return true;

		return false;
	}


}
