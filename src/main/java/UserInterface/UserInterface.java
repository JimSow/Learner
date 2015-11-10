package UserInterface;

import Learner.ILearner;
import Learner.StopCondition;
import Oracle.IOracle;
import be.abeel.util.Pair;
import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.filter.normalize.NormalizeMidrange;
import net.sf.javaml.sampling.Sampling;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by jared on 11/5/15.
 */
public class UserInterface {
	protected int numLearners;

	protected Dataset       data;
	protected Dataset       test;
	protected ILearner      learner;
	protected StopCondition target;
	protected IOracle       oracle;
	protected Classifier    classifier;

	public UserInterface() {
		target = new StopCondition(10,1000, .8);
	}

	public void normalize(double pMiddle, double pRange) {
		NormalizeMidrange nmr = new NormalizeMidrange(pMiddle, pRange);
		nmr.build(data);
		nmr.filter(data);
	}


	public void setTarget(int pMinNumTestsToRun, int pMaxNumTestsToRun, int pTargetAccuract, int pTargetRecall) {
		target = new StopCondition(pMinNumTestsToRun, pMaxNumTestsToRun,pTargetAccuract, pTargetRecall);
	}
	public void setTarget    (StopCondition pTarget ) { target     = pTarget;     }
	public void setLearner   (ILearner pLearner     ) { learner    = pLearner;    }
	public void setClassifier(Classifier pClassifier) { classifier = pClassifier; }
	public void setOracle    (IOracle pOracle       ) { oracle     = pOracle;     }
	public void setDataset   (Dataset pData         ) { data       = pData;       }
	public void setTestData  (Dataset pTest         ) { test       = pTest;       }

	public StopCondition getTarget    () { return target;     }
	public ILearner      getLearner   () { return learner;    }
	public Classifier    getClassifier() { return classifier; }
	public IOracle       getOracle    () { return oracle;     }
	public Dataset       getData      () { return data;       }
	public Dataset       getTestData  () { return test;       }

	protected void runLearner() {
		learner.run();
	}

}
