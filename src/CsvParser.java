import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * This class extends abstract Parser class and is used to parse and extract
 * data from CSV file
 * 
 * @author mandeepahlawat
 *
 */
public class CsvParser extends Parser {

	/**
	 * Constructor is used to initialize headers and data Lists along with the
	 * filename of the file to be parsed.
	 * 
	 * @param filename - String name of the file to be parsed.
	 */
	public CsvParser(String filename) {
		this.filename = filename;
		this.headers = new ArrayList<String>();
		this.data = new ArrayList<HashMap<String, String>>();
	}

	/**
	 * This method is used to return the extracted data.
	 * 
	 * @return data - A list of HashMap, which basically has extracted header as
	 *         keys and data as values.
	 */
	@Override
	public List<HashMap<String, String>> getData() {
		return data;
	}

	/**
	 * This method is used to parse and build headers and data Lists
	 * 
	 * @throws IOException - If file is not found.
	 */
	@Override
	public void parse() throws IOException {
		CSVReader reader = new CSVReader(new BufferedReader(new FileReader(filename)));

		String[] nextLine;
		int rowNumber = 0;
		while ((nextLine = reader.readNext()) != null) {
			if (nextLine != null) {
				if (rowNumber == 0) {
					for (String text : nextLine) {
						headers.add(text);
					}
				} else {
					HashMap<String, String> row = new HashMap<String, String>();
					for (int i = 0; i < nextLine.length; ++i) {
						row.put(headers.get(i), nextLine[i]);
					}
					data.add(row);
				}
			}
			rowNumber++;
		}

	}
}
