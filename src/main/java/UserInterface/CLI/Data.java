package UserInterface.CLI;

import asg.cliche.Command;
import be.abeel.util.Pair;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.sampling.Sampling;
import net.sf.javaml.tools.data.ARFFHandler;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by jared on 11/5/15.
 */
public class Data extends ACommand {
	public Data(CLI pCLI) {
		super(pCLI);
	}

	@Override
	public String getCommands() {
		String commands = "";

		commands += "iris  -- Uses the Iris  data set   \n";
		commands += "adult -- Uses the Adult data set   \n";
		commands += "exit  -- Moves on to the next item \n";



		return commands;
	}

	@Command
	public String iris() {
		try {
			splitData(ARFFHandler.loadARFF(new File("data/Iris.arff"), 4));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "Set Data to the Iris Data set";
	}

	@Command
	public String adult() {
		try {
			splitData(ARFFHandler.loadARFF(new File("data/Iris.arff"), 14));
//			cli.setDataset(ARFFHandler.loadARFF(new File("data/Adult.arff"), 4));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "Set Data to the Iris Data set";
	}

	@Command
	public String data(String path, int classLoc) {
		try {
			 cli.setDataset(ARFFHandler.loadARFF(new File(path), classLoc));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		return "Set data to data at " + path;
	}

	private void splitData(Dataset pData) {
		Sampling s = Sampling.SubSampling;
		Pair<Dataset, Dataset> data = s.sample(pData, (int) (pData.size() * 0.8));

		cli.setDataset (data.x());
		cli.setTestData(data.y());
	}
}
