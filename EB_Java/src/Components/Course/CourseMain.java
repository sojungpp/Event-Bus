/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Common.Constants;
import Common.RegistrationData;
import Components.Registration.Registration;
import Components.Student.Student;
import Components.Student.StudentComponent;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class CourseMain {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(Constants.EVENT_BUS);
		long componentId = eventBus.register();
		System.out.println(Constants.COURSE_MAIN + componentId + Constants.SUCCESFULLY_REGISTERED);

		CourseComponent coursesList = new CourseComponent(Constants.COURSE_SOURCE_FILE_NAME);
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			int size = eventQueue.getSize();
			for (int i = 0; i < size; i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListCourses:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
					break;
				case RegisterCourses:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case CourseInfoForRegistration:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.RegisterClass, getCourseInfo(coursesList, event.getMessage())));
					break;
				case DeleteCourses:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteCourse(coursesList, event.getMessage())));
					break;
				case QuitTheSystem:
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static String getCourseInfo(CourseComponent coursesList, String message) {
		String courseId = getCourseId(message);
		for (int i = 0; i < coursesList.vCourse.size(); i++) {
			Course course = (Course) coursesList.vCourse.get(i);
			if(course.match(courseId)) return message+"/"+course.getString();
		} return message+Constants.SEPARATOR+Constants.NO_INFORMATION;
	}
	private static String getCourseId(String message) {
		String[] split = message.split(Constants.SEPARATOR);
		String registrationInfo = split[0];
		Registration registration = new Registration(registrationInfo);
		return registration.getRegisterCourse().get(0);
	}
	private static String deleteCourse(CourseComponent coursesList, String courseId) {
		for (int i = 0; i < coursesList.vCourse.size(); i++) {
			Course course = (Course) coursesList.vCourse.get(i);
			if (course.match(courseId) && coursesList.vCourse.remove(course)) return courseId+Constants.DELETE_COURSE;
		}  return Constants.NO_COURSE_ID;
	}
	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			return Constants.ADD_COURSE;
		} else
			return Constants.ALREADY_ADD_COURSE;
	}
	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = Constants.BLANK;
		for (int j = 0; j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + Constants.LINE_BREAK;
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				Constants.START_LOG + comment + Constants.EVENT_ID + event.getEventId() + Constants.LOG_MESSAGE + event.getMessage());
	}
}
