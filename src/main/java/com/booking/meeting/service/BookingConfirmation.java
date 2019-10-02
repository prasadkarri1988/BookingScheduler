package com.booking.meeting.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.meeting.model.BookingModel;
import com.booking.meeting.model.BookingOfficeHrsModel;
import com.booking.meeting.utility.BookingUtility;

/**
 * BookingConfirmation  service class 
 * responsible for handling all validation based on business logic.
 * Store the response data in data structure.
 *
 */

public class BookingConfirmation {

	// This method is for storing booking info map and list key is day values date
	// of booking object for each day.
	
	public Map<String, List<BookingModel>> confirmBooking(BookingOfficeHrsModel bookingOfficeHrs) {
		List<BookingModel> bookingList = bookingOfficeHrs.getBookingModelList();
		List<BookingModel> dayList = null;
		Map<String, List<BookingModel>> dataStore = new HashMap<String, List<BookingModel>>();
		List<BookingModel> overloadList = null;
		BookingUtility bookingUtility = new BookingUtility();

		for (BookingModel bookingModel : bookingList) {
			boolean status = bookingUtility.checkOfficeHours(bookingModel, bookingOfficeHrs);
			if (!status) {
				break;
			}

			String bookingDay = bookingModel.getBookingday();

			if (dataStore.get(bookingDay) != null) {
				overloadList = bookingUtility.validateNewBookingTimesBetween(bookingModel, dataStore.get(bookingDay));
				dataStore.put(bookingDay, overloadList);
			} else {
				dayList = new ArrayList<BookingModel>();
				dayList.add(bookingModel);
				dataStore.put(bookingDay, dayList);
			}
		}
		return dataStore;
	}

}