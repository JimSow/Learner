package UserInterface.CLI;

import asg.cliche.Command;
import net.sf.javaml.classification.KNearestNeighbors;

/**
 * Created by jared on 11/5/15.
 */
public class Classifiers extends ACommand {

	public Classifiers(CLI pCLI) {
		super(pCLI);
	}

	@Override
	public String getCommands() {
		String commands = "";

		commands += "KNN   -- Uses the KNN classifier   \n";
		commands += "exit  -- Moves on to the next item \n";

		return commands;
	}

	@Command
	public String KNN() {
		cli.setClassifier(new KNearestNeighbors(3));

		return "Set Classifier to KNN";
	}
}
