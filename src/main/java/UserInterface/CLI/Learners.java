package UserInterface.CLI;

import Learner.PoolLearning.UncertaintySampling.Entropy;
import Learner.PoolLearning.UncertaintySampling.LeastConfident;
import Learner.PoolLearning.UncertaintySampling.Margin;
import asg.cliche.Command;

/**
 * Created by jared on 11/5/15.
 */
public class Learners extends ACommand {

	public Learners(CLI pCLI) {
		super(pCLI);
	}

	@Override
	public String getCommands() {
		String commands = "";

		commands += "Entropy         -- Uses the Entropy Learner        \n";
		commands += "Margin          -- Uses the Margin Learner         \n";
		commands += "Least Confident -- Uses the Least Confident Learner\n";
		commands += "exit            -- Moves on to the next item       \n";


		return commands;
	}

	@Command
	public String Entropy() {

		cli.setLearner(new Entropy(cli.getOracle(), cli.getClassifier(), cli.getData()
				, cli.getTestData(), cli.getTarget(), 10));

		return "Set learner to Entropy";
	}

	@Command
	public String Margin() {

		cli.setLearner(new Margin(cli.getOracle(), cli.getClassifier(), cli.getData()
				, cli.getTestData(), cli.getTarget(), 10));

		return "Set learner to Margin";
	}

	@Command
	public String LeastConfident() {

		cli.setLearner(new LeastConfident(cli.getOracle(), cli.getClassifier(), cli.getData()
				, cli.getTestData(), cli.getTarget(), 10));

		return "Set learner to Least Confident";
	}
}
