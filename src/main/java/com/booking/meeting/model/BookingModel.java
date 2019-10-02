package com.booking.meeting.model;
import java.util.Date;

public class BookingModel {
	
	Date submissionDay;
	String empId;
	String bookingday;
	Date bookingDayDate;
    Date bookingStartTime;
    Date bookingEndTime;
    
	public BookingModel(Date submissionDay, String empId, String bookingday, Date bookingDayDate, Date bookingStartTime,
			Date bookingEndTime) {
		super();
		this.submissionDay = submissionDay;
		this.empId = empId;
		this.bookingday = bookingday;
		this.bookingDayDate = bookingDayDate;
		this.bookingStartTime = bookingStartTime;
		this.bookingEndTime = bookingEndTime;
	}
	
	public Date getSubmissionDay() {
		return submissionDay;
	}

	public void setSubmissionDay(Date submissionDay) {
		this.submissionDay = submissionDay;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getBookingday() {
		return bookingday;
	}

	public void setBookingday(String bookingday) {
		this.bookingday = bookingday;
	}

	public Date getBookingDayDate() {
		return bookingDayDate;
	}

	public void setBookingDayDate(Date bookingDayDate) {
		this.bookingDayDate = bookingDayDate;
	}

	public Date getBookingStartTime() {
		return bookingStartTime;
	}

	public void setBookingStartTime(Date bookingStartTime) {
		this.bookingStartTime = bookingStartTime;
	}

	public Date getBookingEndTime() {
		return bookingEndTime;
	}

	public void setBookingEndTime(Date bookingEndTime) {
		this.bookingEndTime = bookingEndTime;
	}
	
}
