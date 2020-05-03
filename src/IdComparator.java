import java.util.Comparator;
import java.util.HashMap;

/**
 * This is a comparator class which is used to sort the merged data based on the
 * IDs.
 * 
 * @author mandeepahlawat
 *
 */
public class IdComparator implements Comparator<HashMap<String, String>> {
	/**
	 * This method gets ID from data and return corresponding value to sort data
	 */
	@Override
	public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
		Integer id1 = Integer.parseInt(o1.get("ID"));
		Integer id2 = Integer.parseInt(o2.get("ID"));

		return id1.compareTo(id2);
	}
}
