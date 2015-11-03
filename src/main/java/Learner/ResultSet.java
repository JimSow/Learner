package Learner;

import Old.Learner.Learner;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;

import java.util.Map;

/**
 * Created by jared on 10/22/15.
 */
public class ResultSet {
	double Accuracy;
	double BCR;
	double Correlation;
	double CorrelationCoefficient;
	double Cost;
	double ErrorRate;
	double FMeasure;
	double FNRate;
	double FPRate;
	double Precision;
	double Q9;
	double Recall;
	double TNRate;
	double Total;
	double TPRate;

	int NumTests;

	public ResultSet(Map<Object, PerformanceMeasure> map, int pNumTestsRun) {
		Accuracy 			   = 0;
		BCR 				   = 0;
		Correlation 		   = 0;
		CorrelationCoefficient = 0;
		Cost                   = 0;
		ErrorRate 			   = 0;
		FMeasure 			   = 0;
		FNRate 				   = 0;
		FPRate 				   = 0;
		Precision			   = 0;
		Q9 					   = 0;
		Recall 				   = 0;
		TNRate 				   = 0;
		Total 				   = 0;
		TPRate 				   = 0;

		NumTests = pNumTestsRun;

		int i = 0;

		for (Object o : map.keySet()) {
			Accuracy               += map.get(o).getAccuracy();
			BCR                    += map.get(o).getBCR();
			Correlation            += map.get(o).getCorrelation();
			CorrelationCoefficient += map.get(o).getCorrelationCoefficient();
			Cost                   += map.get(o).getCost();
			ErrorRate              += map.get(o).getErrorRate();
			FMeasure               += map.get(o).getFMeasure();
			FNRate                 += map.get(o).getFNRate();
			FPRate                 += map.get(o).getFPRate();
			Precision              += map.get(o).getPrecision();
			Q9                     += map.get(o).getQ9();
			Recall                 += map.get(o).getRecall();
			TNRate                 += map.get(o).getTNRate();
			Total                  += map.get(o).getTotal();
			TPRate                 += map.get(o).getTPRate();

			i++;
		}

		Accuracy 			   /= i;
		BCR      			   /= i;
		Correlation            /= i;
		CorrelationCoefficient /= i;
		Cost                   /= i;
		ErrorRate              /= i;
		FMeasure               /= i;
		FNRate                 /= i;
		FPRate                 /= i;
		Precision              /= i;
		Q9                     /= i;
		Recall                 /= i;
		TNRate                 /= i;
		Total                  /= i;
		TPRate                 /= i;
	}

	public double getAccuracy() 				{ return Accuracy;				 }
	public double getBCR() 						{ return  BCR; 					 }
	public double getCorrelation() 				{ return Correlation; 			 }
	public double getCorrelationCoefficient()   { return CorrelationCoefficient; }
	public double getCost()                     { return Cost; 					 }
	public double getErrorRate() 			    { return ErrorRate;				 }
	public double getFMeasure() 				{ return FMeasure;				 }
	public double getFNRate() 					{ return FNRate;				 }
	public double getFPRate() 					{ return FPRate;				 }
	public double getPrecision()				{ return Precision;				 }
	public double getQ9() 						{ return Q9;					 }
	public double getRecall() 					{ return Recall;				 }
	public double getTNRate() 					{ return TNRate;				 }
	public double getTotal() 					{ return Total;					 }
	public double getTPRate() 					{ return TPRate;				 }

	public int getNumTests() { return NumTests;	}

	public void printResults() {
		System.out.println();
		System.out.println();
		System.out.println("Results:");
		System.out.println("\tAccuracy: ================ " + Accuracy);
		System.out.println("\tBCR: --------------------- " + BCR);
		System.out.println("\tCorrelation: ============= " + Correlation);
		System.out.println("\tCorrelation Coefficient: - " + CorrelationCoefficient);
		System.out.println("\tCost: ==================== " + Cost);
		System.out.println("\tError rate: -------------- " + ErrorRate);
		System.out.println("\tF Measure: =============== " + FMeasure);
		System.out.println("\tFN Rate: ----------------- " + FNRate);
		System.out.println("\tFP Rate: ================= " + FPRate);
		System.out.println("\tPrecision: --------------- " + Precision);
		System.out.println("\tQ9: ====================== " + Q9);
		System.out.println("\tRecall: ------------------ " + Recall);
		System.out.println("\tTN Rate: ================= " + TNRate);
		System.out.println("\tTotal: ------------------- " + Total);
		System.out.println("\tTP Rate: ================= " + TPRate);
		System.out.println();
		System.out.println("Total Number of tests run: - " + NumTests);
		System.out.println();
		System.out.println();
	}
}
