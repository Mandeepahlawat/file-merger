import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * This is the driver class which accepts the file names to be parsed and then
 * returns the combined merged data into a CSV file
 * 
 * @author mandeepahlawat
 *
 */
public class RecordMerger {

	/**
	 * Name of the file where merged data is to be stored.
	 */
	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * A List of HashMap which contains combined merged data.
	 */
	private List<HashMap<String, String>> combinedData;

	/**
	 * A Set which contains all the header values present in different types of
	 * input files.
	 */
	private Set<String> combinedHeader;

	/**
	 * Constructor which is used to initialize combinedData and combinedHeader Lists
	 */
	RecordMerger() {
		this.combinedData = new ArrayList<>();
		this.combinedHeader = new HashSet<String>();
	}

	/**
	 * A utility method which adds data from currently parsed file to the
	 * combinedData list.
	 * 
	 * It checks if the a particular record already exists by ID in the combinedData
	 * then it consolidates its value by including data from header values which
	 * currently doesn't exist in the combinedData
	 * 
	 * If record with that ID doesn't exists in the combinedData then it just adds
	 * that into the combinedData.
	 * 
	 * @param data - All data extracted from currently parsed file
	 */
	private void addData(List<HashMap<String, String>> data) {
		// Add headers from currently parsed file.
		combinedHeader.addAll(data.get(0).keySet());

		for (HashMap<String, String> row : data) {
			HashMap<String, String> existingRecord = getRecord(row.get("ID"));
			HashMap<String, String> newRecord = new HashMap<String, String>();

			if (existingRecord != null) {
				for (String header : combinedHeader) {
					if (existingRecord.get(header) == null) {
						newRecord.put(header, row.get(header));
					} else {
						newRecord.put(header, existingRecord.get(header));
					}
				}
				combinedData.remove(existingRecord);
			} else {
				for (String header : combinedHeader) {
					newRecord.put(header, row.get(header));
				}
			}
			combinedData.add(newRecord);
		}
	}

	/**
	 * A utility method which checks if combinedData list already consists a
	 * particular record by ID
	 * 
	 * @param id - ID of the record to be checked.
	 * @return If record exists then it returns that record otherwise returns null.
	 */
	private HashMap<String, String> getRecord(String id) {
		HashMap<String, String> record = null;
		for (HashMap<String, String> data : combinedData) {
			if (data.get("ID").equalsIgnoreCase(id)) {
				return data;
			}
		}
		return record;
	}

	/**
	 * This method is used to write combined data to a CSV file.
	 * 
	 * It outputs combinedHeader values directly in the CSV file but to output
	 * combinedData values it iterated over the headers list and build a list so
	 * that all the data values are under correct header
	 * 
	 * @throws IOException
	 */
	private void writeCsv() throws IOException {
		CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(FILENAME_COMBINED)));

		String[] headers = combinedHeader.toArray(new String[0]);
		writer.writeNext(headers);

		for (int i = 0; i < combinedData.size(); ++i) {
			ArrayList<String> data = new ArrayList<String>();
			for (String header : headers) {
				data.add(combinedData.get(i).get(header));
			}
			writer.writeNext(data.toArray(new String[0]));

		}
		writer.close();
	}

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		RecordMerger recordMerger = new RecordMerger();

		try {
			for (String filename : args) {
				String extension = filename.split("\\.")[1];
				if (extension.equals("html")) {
					HtmlParser htmlParser = new HtmlParser(filename);
					htmlParser.parse();
					recordMerger.addData(htmlParser.getData());
				}
				if (extension.equals("csv")) {
					CsvParser csvParser = new CsvParser(filename);
					csvParser.parse();
					recordMerger.addData(csvParser.getData());
				}
			}

			Collections.sort(recordMerger.combinedData, new IdComparator());
			recordMerger.writeCsv();
			System.out.println("Combined merged data stored in " + recordMerger.FILENAME_COMBINED);
		} catch (IOException e) {
			System.out.println("Please enter correct file names.");
		}

	}
}
