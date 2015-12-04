package DataAccess.Database;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by jared on 11/19/15.
 */
@Entity("DataSet")
public class MongoDataSet {
	public final int NUM = 105;

	@Id
	int id;
	String name;
	Object classValue;

	double classCount;
	double classEntropy;

	double DecisionStumpAUC;
	double DecisionStumpErrRate;
	double DecisionStumpKappa;

	double DefaultAccuracy;
	double Dimensionality;

	double EquivalentNumberOfAtts;

	double HoeffdingAdwinChanges;
	double HoeffdingAdwinWarnings;
	double HoeffdingDDMChanges;
	double HoeffdingDDMWarnings;

	double IncompleteInstanceCount;
	double InstanceCount;

	double J4800001AUC;
	double J4800001ErrRate;
	double J4800001Kappa;
	double J480001AUC;
	double J480001ErrRate;
	double J480001Kappa;
	double J48001AUC;
	double J48001ErrRate;
	double J48001Kappa;

	double JripAUC;
	double JripErrRate;
	double JripKappa;

	double MajorityClassSize;
	double MaxNominalAttDistinctValues;
	double MeanAttributeEntropy;
	double MeanKurtosisOfNumericAtts;
	double MeanMeansOfNumericAtts;
	double MeanMutualInformation;
	double MeanNominalAttDistinctValues;
	double MeanSkewnessOfNumericAtts;
	double MeanStdDevOfNumericAtts;
	double MinNominalAttDistinctValues;
	double MinorityClassSize;

	double NBTreeAUC;
	double NBTreeErrRate;
	double NBTreeKappa;

	double NaiveBayesAUC;
	double NaiveBayesAdwinChanges;
	double NaiveBayesAdwinWarnings;
	double NaiveBayesDdmChanges;
	double NaiveBayesDdmWarnings;
	double NaiveBayesErrRate;
	double NaiveBayesKappa;

	double NegativePercentage;
	double NoiseToSignalRatio;
	double NumAttributes;
	double NumBinaryAttributes;
	double NumMissingValues;
	double NumNominalAtts;
	double NumNumericAtts;
	double NumberOfClasses;
	double NumberOfFeatures;
	double NumberOfInstances;
	double NumberOfInstancesWithMissingValues;
	double NumberOfMissingValues;
	double NumberOfNumericFeatures;

	double PercentageOfBinaryAtts;
	double PercentageOfMissingValues;
	double PercentageOfNominalAtts;
	double PercentageOfNumericAtts;
	double PositivePercentage;

	double RepTreeDepth1AUC;
	double RepTreeDepth1ErrRate;
	double RepTreeDepth1Kappa;

	double RepTreeDepth2AUC;
	double RepTreeDepth2ErrRate;
	double RepTreeDepth2Kappa;

	double RepTreeDepth3AUC;
	double RepTreeDepth3ErrRate;
	double RepTreeDepth3Kappa;

	double RandomTreeDepth1AUC;
	double RandomTreeDepth1ErrRate;
	double RandomTreeDepth1Kappa;

	double RandomTreeDepth2AUC;
	double RandomTreeDepth2ErrRate;
	double RandomTreeDepth2Kappa;

	double RandomTreeDepth3AUC;
	double RandomTreeDepth3ErrRate;
	double RandomTreeDepth3Kappa;

	double SVMe1AUC;
	double SVMe1ErrRate;
	double SVMe1Kappa;

	double SVMe2AUC;
	double SVMe2ErrRate;
	double SVMe2Kappa;

	double SVMe3AUC;
	double SVMe3ErrRate;
	double SVMe3Kappa;

	double SimpleLogisticAUC;
	double SimpleLogisticErrRate;
	double SimpleLogisticKappa;

	double StdvNominalAttDistinctValues;

	double Knn1NAUC;
	double Knn1NErrRate;
	double Knn1NKappa;

	double Knn2NAUC;
	double Knn2NErrRate;
	double Knn2NKappa;

	double Knn3NAUC;
	double Knn3NErrRate;
	double Knn3NKappa;

	public Instance getAsInstance() {
		Instance instance = new SparseInstance(NUM, 0, classValue);

		for(double value : getValues()) {
			instance.add(value);
		}

		return instance;
	}

	private double[] getValues() {
		double[] values = new double[NUM];


		values[  0] = classCount;
		values[  1] = classEntropy;

		values[  2] = DecisionStumpAUC;
		values[  3] = DecisionStumpErrRate;
		values[  4] = DecisionStumpKappa;

		values[  5] = DefaultAccuracy;
		values[  6] = Dimensionality;

		values[  7] = EquivalentNumberOfAtts;

		values[  8] = HoeffdingAdwinChanges;
		values[  9] = HoeffdingAdwinWarnings;
		values[ 10] = HoeffdingDDMChanges;
		values[ 11] = HoeffdingDDMWarnings;

		values[ 12] = IncompleteInstanceCount;
		values[ 13] = InstanceCount;

		values[ 14] = J4800001AUC;
		values[ 15] = J4800001ErrRate;
		values[ 16] = J4800001Kappa;
		values[ 17] = J480001AUC;
		values[ 18] = J480001ErrRate;
		values[ 19] = J480001Kappa;
		values[ 20] = J48001AUC;
		values[ 21] = J48001ErrRate;
		values[ 22] = J48001Kappa;

		values[ 23] = JripAUC;
		values[ 24] = JripErrRate;
		values[ 25] = JripKappa;

		values[ 26] = MajorityClassSize;
		values[ 27] = MaxNominalAttDistinctValues;
		values[ 28] = MeanAttributeEntropy;
		values[ 29] = MeanKurtosisOfNumericAtts;
		values[ 30] = MeanMeansOfNumericAtts;
		values[ 31] = MeanMutualInformation;
		values[ 32] = MeanNominalAttDistinctValues;
		values[ 33] = MeanSkewnessOfNumericAtts;
		values[ 34] = MeanStdDevOfNumericAtts;
		values[ 35] = MinNominalAttDistinctValues;
		values[ 36] = MinorityClassSize;

		values[ 37] = NBTreeAUC;
		values[ 38] = NBTreeErrRate;
		values[ 39] = NBTreeKappa;

		values[ 40] = NaiveBayesAUC;
		values[ 41] = NaiveBayesAdwinChanges;
		values[ 42] = NaiveBayesAdwinWarnings;
		values[ 43] = NaiveBayesDdmChanges;
		values[ 44] = NaiveBayesDdmWarnings;
		values[ 45] = NaiveBayesErrRate;
		values[ 46] = NaiveBayesKappa;

		values[ 47] = NegativePercentage;
		values[ 48] = NoiseToSignalRatio;
		values[ 49] = NumAttributes;
		values[ 50] = NumBinaryAttributes;
		values[ 51] = NumMissingValues;
		values[ 52] = NumNominalAtts;
		values[ 53] = NumNumericAtts;
		values[ 54] = NumberOfClasses;
		values[ 55] = NumberOfFeatures;
		values[ 56] = NumberOfInstances;
		values[ 57] = NumberOfInstancesWithMissingValues;
		values[ 58] = NumberOfMissingValues;
		values[ 59] = NumberOfNumericFeatures;

		values[ 60] = PercentageOfBinaryAtts;
		values[ 61] = PercentageOfMissingValues;
		values[ 62] = PercentageOfNominalAtts;
		values[ 63] = PercentageOfNumericAtts;
		values[ 64] = PositivePercentage;

		values[ 65] = RepTreeDepth1AUC;
		values[ 66] = RepTreeDepth1ErrRate;
		values[ 67] = RepTreeDepth1Kappa;

		values[ 68] = RepTreeDepth2AUC;
		values[ 69] = RepTreeDepth2ErrRate;
		values[ 70] = RepTreeDepth2Kappa;

		values[ 71] = RepTreeDepth3AUC;
		values[ 72] = RepTreeDepth3ErrRate;
		values[ 73] = RepTreeDepth3Kappa;

		values[ 74] = RandomTreeDepth1AUC;
		values[ 75] = RandomTreeDepth1ErrRate;
		values[ 76] = RandomTreeDepth1Kappa;

		values[ 77] = RandomTreeDepth2AUC;
		values[ 78] = RandomTreeDepth2ErrRate;
		values[ 79] = RandomTreeDepth2Kappa;

		values[ 80] = RandomTreeDepth3AUC;
		values[ 81] = RandomTreeDepth3ErrRate;
		values[ 82] = RandomTreeDepth3Kappa;

		values[ 83] = SVMe1AUC;
		values[ 84] = SVMe1ErrRate;
		values[ 85] = SVMe1Kappa;

		values[ 86] = SVMe2AUC;
		values[ 87] = SVMe2ErrRate;
		values[ 88] = SVMe2Kappa;

		values[ 89] = SVMe3AUC;
		values[ 90] = SVMe3ErrRate;
		values[ 91] = SVMe3Kappa;

		values[ 92] = SimpleLogisticAUC;
		values[ 93] = SimpleLogisticErrRate;
		values[ 94] = SimpleLogisticKappa;

		values[ 95] = StdvNominalAttDistinctValues;

		values[ 96] = Knn1NAUC;
		values[ 97] = Knn1NErrRate;
		values[ 98] = Knn1NKappa;

		values[ 99] = Knn2NAUC;
		values[100] = Knn2NErrRate;
		values[101] = Knn2NKappa;

		values[102] = Knn3NAUC;
		values[103] = Knn3NErrRate;
		values[104] = Knn3NKappa;

		return values;
	}
}
