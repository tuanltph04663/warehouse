package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompareDate {

	public static boolean commpareDate(String dateToCommpare, String date) {
		// dateToCommpare: dd-MM-yyyy
		// date: dd-MM-yyyy
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			date1 = sdf.parse(dateToCommpare);
			date2 = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (date1.compareTo(date2) > 0 || date1.compareTo(date2) == 0) {
			System.out.println("Date1 is after Date2");
			return true;
		}
		return false;
	}
	
}
