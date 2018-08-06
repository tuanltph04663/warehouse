package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

	public static Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date d = new Date();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public static void main(String[] args) {
		Date date = Convert.stringToDate("02-03-2015");
		System.out.println(date.getDate());
		System.out.println(date.getMonth());
		System.out.println(date.getYear());
	}
	
}
