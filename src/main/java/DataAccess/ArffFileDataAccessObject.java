package DataAccess;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.ARFFHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jared on 11/7/15.
 */
public class ArffFileDataAccessObject implements IDataAccessObject {

	private File file;
	private Dataset data;

	public ArffFileDataAccessObject(String filePath) throws IOException {
		file = new File(filePath);

		int classLoc = -1;

		for(String line : getAllLines(file)) {
			if(line.contains("@ATTRIBUTE")) {
				classLoc++;
				if(line.contains("class")) {
					break;
				}
			}
		}
	}
	public ArffFileDataAccessObject(String filePath, int classLoc) throws FileNotFoundException {
		file = new File(filePath);
		data = ARFFHandler.loadARFF(file, classLoc);
	}

	public Instance getInstance(int id) {
		return data.instance(id);
	}

	public Object getLabel(int id) {
		return data.instance(id).classValue();
	}

	//TODO Implement method getDataset(int[] ids)
	public Dataset getDataset(int[] ids) {
		return null;
	}

	public Dataset getDataset() {
		return data;
	}

	public int getCount() {
		return data.size();
	}

	private List<String> getAllLines(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));

		String line = null;
		List<String> lines = new ArrayList<String>();

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		br.close();
		return lines;
	}
}
