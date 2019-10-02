package com.booking.meeting;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.booking.meeting.model.BookingModel;
import com.booking.meeting.model.BookingOfficeHrsModel;
import com.booking.meeting.service.BookingConfirmation;
import com.booking.meeting.utility.BookingUtility;

/**
 * BookingApp
 *
 */
public class BookingApp {

	

	public static void main(String[] args) throws ParseException {

		BookingConfirmation bookingConfirmation = new BookingConfirmation();
		BookingUtility utility = new BookingUtility();
		String input=utility.readDataFromFile();
		BookingOfficeHrsModel bookingOfficeHrs = utility.parseInput(input);
		Map<String, List<BookingModel>> dataStore = bookingConfirmation.confirmBooking(bookingOfficeHrs);
		utility.generateReport(dataStore);

	}

}
