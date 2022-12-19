package com.demo;

import java.util.ArrayList;

public class CourseEnrollment {
	private Course course;
	private ArrayList<User> enrolledUserList;
	
	
	public CourseEnrollment() {
		super();
	}

	public CourseEnrollment(Course course, ArrayList<User> enrolledUserList) {
		super();
		this.course = course;
		this.enrolledUserList = enrolledUserList;
	}

	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public ArrayList<User> getEnrolledUserList() {
		return enrolledUserList;
	}
	
	public void setEnrolledUserList(ArrayList<User> enrolledUserList) {
		this.enrolledUserList = enrolledUserList;
	}
}
