package UserInterface.CLI;

import Oracle.SimpleOracle;
import asg.cliche.Command;

/**
 * Created by jared on 11/5/15.
 */
public class Oracles extends ACommand {

	public Oracles(CLI pCLI) {
		super(pCLI);
	}

	@Override
	public String getCommands() {
		String commands = "";

		commands += "SimpleOracle  -- Uses the Simple Oracle    \n";
		commands += "exit          -- Moves on to the next item \n";

		return commands;
	}

	@Command
	public String SimpleOracle(){
		cli.setOracle(new SimpleOracle(cli.getData()));

		return "Set Oracle to Simple";
	}

}
