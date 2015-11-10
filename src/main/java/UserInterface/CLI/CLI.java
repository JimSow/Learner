package UserInterface.CLI;

import UserInterface.UserInterface;
import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by jared on 11/5/15.
 */
public class CLI extends UserInterface {

	public CLI() {
		super();
	}

	public static void main(String[] args) {
		new CLI().run();
	}

	public void run() {
		Data data       = new Data       (this);
		Oracles oracle  = new Oracles    (this);
		Classifiers cls = new Classifiers(this);
		Learners lrn    = new Learners   (this);

		try {
			System.out.println(getWelcomeMessage());

			ShellFactory.createConsoleShell("Data set"  , data  .getCommands(), data  ).commandLoop();
			ShellFactory.createConsoleShell("Oracle"    , oracle.getCommands(), oracle).commandLoop();
			ShellFactory.createConsoleShell("Classifier", cls   .getCommands(), cls   ).commandLoop();
			ShellFactory.createConsoleShell("Learner"   , lrn   .getCommands(), lrn   ).commandLoop();

		} catch (IOException e) {
			e.printStackTrace();
		}

		runLearner();

		learner.printResult();

	}

	public String getWelcomeMessage() {
		String welcome = "";

		welcome += "     _____  __        ________   ______   _______   __    __     \n";
		welcome += "    /     |/  |      /        | /      \\ /       \\ /  \\  /  | \n";
		welcome += "    $$$$$ |$$ |      $$$$$$$$/ /$$$$$$  |$$$$$$$  |$$  \\ $$ |   \n";
		welcome += "       $$ |$$ |      $$ |__    $$ |__$$ |$$ |__$$ |$$$  \\$$ |   \n";
		welcome += "  __   $$ |$$ |      $$    |   $$    $$ |$$    $$< $$$$  $$ |    \n";
		welcome += " /  |  $$ |$$ |      $$$$$/    $$$$$$$$ |$$$$$$$  |$$ $$ $$ |    \n";
		welcome += " $$ \\__$$ |$$ |_____ $$ |_____ $$ |  $$ |$$ |  $$ |$$ |$$$$ |   \n";
		welcome += " $$    $$/ $$       |$$       |$$ |  $$ |$$ |  $$ |$$ | $$$ |    \n";
		welcome += "  $$$$$$/  $$$$$$$$/ $$$$$$$$/ $$/   $$/ $$/   $$/ $$/   $$/     \n";

		return welcome;
	}
}
