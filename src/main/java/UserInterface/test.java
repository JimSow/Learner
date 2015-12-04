package UserInterface;

import asg.cliche.*;

import java.io.IOException;

/**
 * Created by jared on 11/5/15.
 */
public class test {

	@Command // One,
	public String hello() {
		return "Hello, World!";
	}

	@Command // two,
	public int add(int a, int b) {
		return a + b;
	}

	public String getCommands() {
		String commands = "";

		return commands;
	}

	@Command
	public void e() throws CLIException {
		throw new CLIException("s");
	}

	@Command
	public void exit() {}

	public String getWelcomeMessage() {
		String welcome = "";

		welcome += "     _____  __        ________   ______   _______   __    __ \n";
		welcome += "    /     |/  |      /        | /      \\ /       \\ /  \\  /  |\n";
		welcome += "    $$$$$ |$$ |      $$$$$$$$/ /$$$$$$  |$$$$$$$  |$$  \\ $$ |\n";
		welcome += "       $$ |$$ |      $$ |__    $$ |__$$ |$$ |__$$ |$$$  \\$$ |\n";
		welcome += "  __   $$ |$$ |      $$    |   $$    $$ |$$    $$< $$$$  $$ |\n";
		welcome += " /  |  $$ |$$ |      $$$$$/    $$$$$$$$ |$$$$$$$  |$$ $$ $$ |\n";
		welcome += " $$ \\__$$ |$$ |_____ $$ |_____ $$ |  $$ |$$ |  $$ |$$ |$$$$ |\n";
		welcome += " $$    $$/ $$       |$$       |$$ |  $$ |$$ |  $$ |$$ | $$$ |\n";
		welcome += "  $$$$$$/  $$$$$$$$/ $$$$$$$$/ $$/   $$/ $$/   $$/ $$/   $$/ \n";

		return welcome;
	}

	public static void main(String[] args) throws IOException {
		test t = new test();

		System.out.println(t.getWelcomeMessage());

		Shell consoleShell = ShellFactory.createConsoleShell("", t.getCommands(), t);
		consoleShell.commandLoop();


	}
}
