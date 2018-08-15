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

	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String d = sdf.format(date);
		return d;
	}

	public static java.sql.Date utilDateToSqlDate(Date d) {
		java.sql.Date dateConverted = new java.sql.Date(d.getTime());
		return dateConverted;
	}

	public static void main(String[] args) {
		Date dateInput = stringToDate("12-03-2018");
		Date date = new Date(); // 15-08-2018

		System.out.println(date);
	}

}
