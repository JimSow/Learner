package UserInterface.CLI;

/**
 * Created by jared on 11/5/15.
 */
public abstract class ACommand {

	protected CLI cli;

	public ACommand(CLI pCLI){
		cli = pCLI;
	}

	public abstract String getCommands();

}
