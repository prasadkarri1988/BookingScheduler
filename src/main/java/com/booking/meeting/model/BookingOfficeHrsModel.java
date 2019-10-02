package com.booking.meeting.model;

import java.util.ArrayList;
import java.util.List;

public class BookingOfficeHrsModel {

	int startTime;
	int endTime;
	List<BookingModel> bookingModelList = new ArrayList<BookingModel>();

	public BookingOfficeHrsModel(int startTime, int endTime, List<BookingModel> bookingModelList) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.bookingModelList = bookingModelList;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public List<BookingModel> getBookingModelList() {
		return bookingModelList;
	}

	public void setBookingModelList(List<BookingModel> bookingModelList) {
		this.bookingModelList = bookingModelList;
	}

}
