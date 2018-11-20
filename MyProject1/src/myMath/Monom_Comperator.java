package myMath;

import java.util.Comparator;
/**
 * 
 * @author David Tover
 * This class represent a comparator for comparing Monoms
 */

public class Monom_Comperator implements Comparator<Monom> {

	@Override
	public int compare(Monom arg0, Monom arg1) {
		// TODO Auto-generated method stub

		if(arg0.get_power()>arg1.get_power())
			return -1;
		else if (arg0.get_power()<arg1.get_power())
			return 1;
		return 0;
	}


}
