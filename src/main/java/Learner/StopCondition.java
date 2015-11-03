package Learner;

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
		this(pNumTestsToRun, pNumTestsToRun, 1, 1);
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

	public boolean Stop(ResultSet set, int pNumTests) {
		if(pNumTests < minNumTests)
			return false;
		if(pNumTests >= maxNumTests)
			return true;
		if(set.Accuracy >= targetAccuracy && set.Recall >= targetRecall) {
			return true;
		}
		return false;
	}
}
