package com.sg.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Karoonakar
 *
 */
public class DateUtil {

	public static String getHtmlToString(String travelDate) {

		String outputDate = null;

		if (travelDate != null && travelDate.length() >= 16) {

			String dt = travelDate.substring(0, 16).trim().replace(" ", "-");
			SimpleDateFormat formatter = new SimpleDateFormat("EEE-MMM-dd-yyyy");

			try {

				Date date = formatter.parse(dt);
				SimpleDateFormat outputFormatter = new SimpleDateFormat(
						"dd-MM-yyyy");
				outputDate = outputFormatter.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return outputDate;

		}

		return outputDate;

	}

	public static int getYear(String date) {

		return Integer.parseInt(date.substring(6, 10));

	}

	public static int getMonth(String date) {

		return Integer.parseInt(date.substring(3, 5));

	}

	public static int getDay(String date) {

		return Integer.parseInt(date.substring(0, 2));

	}

}
