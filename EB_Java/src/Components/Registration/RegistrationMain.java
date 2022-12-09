/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Registration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Common.Constants;
import Common.RegistrationData;
import Components.Course.Course;
import Components.Student.Student;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class RegistrationMain {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(Constants.EVENT_BUS);
		long componentId = eventBus.register();
		System.out.println(Constants.REGISTRATION_MAIN + componentId + Constants.SUCCESFULLY_REGISTERED);

		RegistrationComponent registrationList = new RegistrationComponent(Constants.REGISTRATION_SOURCE_FILE_NAME);
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
				case RegisterClass:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerClass(registrationList, event.getMessage())));
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

	private static String registerClass(RegistrationComponent registrationList, String message) {
		RegistrationData registrationData = new RegistrationData(message);
		if(registrationData.getStudentInfo()==null) return Constants.NO_STUDENT_ID;
		if(registrationData.getCourseInfo()==null) return Constants.NO_COURSE_ID;
		ArrayList<String> prerequisiteCourses = registrationData.getCourseInfo().getPrerequisiteCoursesList();
		ArrayList<String> completedCourses = registrationData.getStudentInfo().getCompletedCourses();
		for(int i=0; i<prerequisiteCourses.size(); i++) {
			if(!prerequisiteCourses.contains(completedCourses.get(i))) return Constants.HAVE_TO_REGISTER_PREREQUISITE_COURSE;
		}
		if(!registrationList.vRegistration.add(registrationData.getRegistrationInfo())) return Constants.FAIL_REGISTER_CLASS;
		return Constants.REGISTER_CLASS;
	}

	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				Constants.START_LOG + comment + Constants.EVENT_ID + event.getEventId() + Constants.LOG_MESSAGE + event.getMessage());
	}
}
