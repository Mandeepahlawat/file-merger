import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract class which contains all the common methods and the variables a
 * concrete class should have
 * 
 * @author mandeepahlawat
 *
 */
public abstract class Parser {
	/**
	 * Name of the file to be parsed.
	 */
	protected String filename;

	/**
	 * A list of strings containing all the headers.
	 */
	protected List<String> headers;

	/**
	 * A list of HashMap containing headers as keys and data as values.
	 */
	protected List<HashMap<String, String>> data;

	/**
	 * Abstract method to parse the corresponding file
	 * 
	 * @throws IOException
	 */
	protected abstract void parse() throws IOException;

	/**
	 * Abstract method to return the parsed data
	 * 
	 * @return
	 */
	protected abstract List<HashMap<String, String>> getData();
}
