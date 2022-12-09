/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Components.Common.RegistrationData;
import Components.Registration.Registration;
import Components.Student.Student;
import Components.Student.StudentComponent;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class CourseMain {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("CourseMain (ID:" + componentId + ") is successfully registered...");

		CourseComponent coursesList = new CourseComponent("C:\\\\\\\\\\\\\\\\2022-2\\\\\\\\\\\\\\\\클서\\\\\\\\\\\\\\\\EB_Java\\\\\\\\\\\\\\\\src\\\\\\\\\\\\\\\\Courses.txt");
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
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
					break;
				case RegisterCourses:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case CourseInfoForRegistration:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.RegisterClass, getCourseInfo(coursesList, event.getMessage())));
					break;
				case DeleteCourses:
					printLogEvent("Get", event);
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
		System.out.println("getCourseInfo: " + message);
		String courseId = getCourseId(message);
		for (int i = 0; i < coursesList.vCourse.size(); i++) {
			Course course = (Course) coursesList.vCourse.get(i);
			if(course.match(courseId)) return message+"/"+course.getString();
		} return message+"/noInfo";
	}
	private static String getCourseId(String message) {
		String[] split = message.split("/");
		String registrationInfo = split[0];
		Registration registration = new Registration(registrationInfo);
		return registration.getRegisterCourse().get(0);
	}
	private static String deleteCourse(CourseComponent coursesList, String courseId) {
		for (int i = 0; i < coursesList.vCourse.size(); i++) {
			Course course = (Course) coursesList.vCourse.get(i);
			if (course.match(courseId) && coursesList.vCourse.remove(course)) return courseId+" 과목을 삭제했습니다.";
		}  return "존재하지 않는 courseId 입니다.";
	}
	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			return "This course is successfully added.";
		} else
			return "This course is already registered.";
	}
	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = "";
		for (int j = 0; j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
