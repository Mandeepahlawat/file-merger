import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class extends abstract Parser class and is used to parse and extract
 * data from HTML file
 * 
 * @author mandeepahlawat
 *
 */
public class HtmlParser extends Parser {

	/**
	 * Constructor is used to initialize headers and data Lists along with the
	 * filename of the file to be parsed.
	 * 
	 * @param filename
	 */
	public HtmlParser(String filename) {
		this.filename = filename;
		this.headers = new ArrayList<String>();
		this.data = new ArrayList<HashMap<String, String>>();
	}

	/**
	 * A utility method which adds header elements to the headers list
	 * 
	 * @param columns - parsed header(th) elements from jsoup
	 */
	private void extractHeader(Elements columns) {
		for (Element column : columns) {
			headers.add(column.text());
		}
	}

	/**
	 * A utility method which builds data List from the parsed td elements.
	 * 
	 * @param columns - parsed data(td) elements from jsoup
	 */
	private void extractData(Elements columns) {
		HashMap<String, String> row = new HashMap<String, String>();
		for (int i = 0; i < columns.size(); ++i) {
			row.put(headers.get(i), columns.get(i).text());
		}
		data.add(row);
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
		File input = new File(this.filename);

		Document doc = Jsoup.parse(input, "UTF-8");

		Elements rows = doc.select("tr");
		for (Element row : rows) {
			Elements headerElements = row.select("th");
			extractHeader(headerElements);

			Elements dataElements = row.select("td");
			// first row will be a header so there won't be any td elements
			if (dataElements.size() > 0) {
				extractData(dataElements);
			}
		}

	}
}
