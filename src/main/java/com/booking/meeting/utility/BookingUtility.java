package com.booking.meeting.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.booking.meeting.model.BookingModel;
import com.booking.meeting.model.BookingOfficeHrsModel;

public class BookingUtility {
	
	private DateFormat dateFormatterSeconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dateFormatterHours = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private DateFormat dateFormatterHHMM = new SimpleDateFormat("HH:mm");
	private  boolean newBooking = false;
	
	
	
	public DateFormat getDateFormatterSeconds() {
		return dateFormatterSeconds;
	}


	public void setDateFormatterSeconds(DateFormat dateFormatterSeconds) {
		this.dateFormatterSeconds = dateFormatterSeconds;
	}


	public DateFormat getDateFormatterHours() {
		return dateFormatterHours;
	}


	public void setDateFormatterHours(DateFormat dateFormatterHours) {
		this.dateFormatterHours = dateFormatterHours;
	}


	public DateFormat getDateFormatterHHMM() {
		return dateFormatterHHMM;
	}


	public void setDateFormatterHHMM(DateFormat dateFormatterHHMM) {
		this.dateFormatterHHMM = dateFormatterHHMM;
	}


	public boolean isNewBooking() {
		return newBooking;
	}


	public void setNewBooking(boolean newBooking) {
		this.newBooking = newBooking;
	}


	public BookingOfficeHrsModel parseInput(String input)  {

		String[] inputArray = input.split("\\n");
		BookingOfficeHrsModel bookingOfficeHrs = null;
		try {
			int officeStartTime = Integer.valueOf(inputArray[0].substring(0, 3));
			int officeEndTime = Integer.valueOf(inputArray[0].substring(5));
			List<BookingModel> bookingModelList = new ArrayList<BookingModel>();

			for (int i = 1; i < inputArray.length; i++) {
				String submissionDay = inputArray[i].substring(0, 19);
				Date submissionDayDate = dateFormatterSeconds.parse(submissionDay);
				String empId = inputArray[i].substring(20);
				String bookingDay = inputArray[++i].substring(0, 10);
				Date bookingStartTime = dateFormatterHours.parse(inputArray[i].substring(0, 17));
				String bookingHours = inputArray[i].substring(17);
				Date bookingEndTime = bookingEndtime(bookingStartTime, bookingHours);

				BookingModel bookingModel = new BookingModel(submissionDayDate, empId, bookingDay, bookingStartTime, bookingStartTime, bookingEndTime);
				bookingModelList.add(bookingModel);
				bookingOfficeHrs = new BookingOfficeHrsModel(officeStartTime, officeEndTime, bookingModelList);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return bookingOfficeHrs;

	}
	
	
	private Date bookingEndtime(Date Starttime, String bookingHours) {
		int noHours;
		int noMinutes = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Starttime);
		String[] inputArray = bookingHours.split(":");
		if (inputArray.length > 1) {
			noHours = Integer.parseInt(inputArray[0]);
			noMinutes = Integer.parseInt(inputArray[1]);
		}
		noHours = Integer.parseInt(inputArray[0]);
		calendar.add(Calendar.HOUR_OF_DAY, noHours);
		calendar.add(Calendar.MINUTE, noMinutes);
		return calendar.getTime();
	}
	
	
	public void generateReport(Map<String, List<BookingModel>> dataStore) {
		for (Map.Entry<String, List<BookingModel>> entry : dataStore.entrySet()) {

			List<BookingModel> dayList = entry.getValue();
			for (int i = 0; i < dayList.size(); i++) {
				BookingModel bookingModel = dayList.get(i);
				if (i == 0) {
					System.out.println(bookingModel.getBookingday());
					System.out.print(dateFormatterHHMM.format(bookingModel.getBookingStartTime()));
					System.out.print(" " + dateFormatterHHMM.format(bookingModel.getBookingEndTime()));
					System.out.println(" " + bookingModel.getEmpId());
				} else {
					System.out.print(dateFormatterHHMM.format(bookingModel.getBookingStartTime()));
					System.out.print(" " + dateFormatterHHMM.format(bookingModel.getBookingEndTime()));
					System.out.println(" " + bookingModel.getEmpId());
				}
			}
		}
	}
	
	
	@SuppressWarnings("unused")
	public boolean checkOfficeHours(BookingModel model, BookingOfficeHrsModel bookingOfficeHrs) {
		int mettingStartingTime = model.getBookingStartTime().getHours() * 100;
		int MettingEndTime = model.getBookingEndTime().getHours() * 100;
		if (mettingStartingTime >= bookingOfficeHrs.getStartTime() && MettingEndTime <= bookingOfficeHrs.getEndTime()) {
			return true;
		}
		return false;

	}
	
	
	private boolean validateSubmissionDay(BookingModel oldBookingModel, BookingModel newBookingModel) {
		boolean overloadFlag = false;
		if (oldBookingModel.getSubmissionDay().after(newBookingModel.getSubmissionDay())) {
			overloadFlag = true;
		}
		return overloadFlag;
	}

	
	    // Validate Time intervals 
		public List<BookingModel> validateNewBookingTimesBetween(BookingModel bookingModel, List<BookingModel> list) {
			for (int i = 0; i < list.size(); i++) {
				BookingModel bModel = list.get(i);
				if (bModel.getBookingStartTime().before(bookingModel.getBookingStartTime())
						&& bModel.getBookingEndTime().after(bookingModel.getBookingStartTime())) {
					newBooking = true;
					boolean overloadFlag = validateSubmissionDay(bModel, bookingModel);
					if (overloadFlag) {
						list.set(i, bookingModel);
					}
					break;
				}

				if (bookingModel.getBookingStartTime().equals(bModel.getBookingStartTime())
						&& bookingModel.getBookingEndTime().after(bModel.getBookingEndTime())) {

					boolean overloadFlag = validateSubmissionDay(bModel, bookingModel);
					newBooking = true;
					if (overloadFlag) {
						list.set(i, bookingModel);
						newBooking = true;
					}
					break;
				}

				if (bModel.getBookingStartTime().before(bookingModel.getBookingEndTime())
						&& bModel.getBookingEndTime().after(bookingModel.getBookingEndTime())) {
					newBooking = true;
					boolean overloadFlag = validateSubmissionDay(bModel, bookingModel);
					if (overloadFlag) {
						list.set(i, bookingModel);
						newBooking = true;
					}
					break;
				}

				if (bModel.getBookingStartTime().equals(bookingModel.getBookingStartTime())
						&& bModel.getBookingEndTime().equals(bookingModel.getBookingEndTime())) {
					boolean overloadFlag = validateSubmissionDay(bModel, bookingModel);
					if (overloadFlag) {
						list.set(i, bookingModel);
						newBooking = true;
					}
					break;
				}

			}
			if (!newBooking) {
				list.add(bookingModel);
			}
			return list;

		}
		
		
		public String readDataFromFile() {
			InputStream inputStream = Thread.class.getResourceAsStream("/input.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

			String secondLine, input = null;
			BufferedReader br;
			try {
				br = new BufferedReader(inputStreamReader);
				while ((secondLine = br.readLine()) != null) {
					if (input != null) {
						input = input + secondLine + "\n";
					} else {
						input = secondLine + "\n";
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return input;
		}

}
