/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import Common.Constants;
import Components.Course.Course;
import Components.Course.CourseComponent;
import Components.Registration.Registration;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class StudentMain {
	public static void main(String args[]) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(Constants.EVENT_BUS);
		long componentId = eventBus.register();
		System.out.println(Constants.STUDENT_MAIN + componentId + Constants.SUCCESFULLY_REGISTERED);

		StudentComponent studentsList = new StudentComponent(Constants.STUDENT_SOURCE_FILE_NAME);
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
				case ListStudents:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeStudentList(studentsList)));
					break;
				case RegisterStudents:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerStudent(studentsList, event.getMessage())));
					break;
				case DeleteStudents:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage())));
					break;
				case StudentInfoForRegistration:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.sendEvent(new Event(EventId.CourseInfoForRegistration, getStudentInfo(studentsList, event.getMessage())));
					break;
				case QuitTheSystem:
					printLogEvent(Constants.LOG_COMMENT_GET, event);
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}
	private static String getStudentInfo(StudentComponent studentsList, String message) {
		Registration registration = new Registration(message);
		String studentId = registration.getStudentId();
		for (int i = 0; i < studentsList.vStudent.size(); i++) {
			Student student = (Student) studentsList.vStudent.get(i);
			if(student.match(studentId)) return message + Constants.SEPARATOR + student.getString();
		} return message + Constants.SEPARATOR+Constants.NO_INFORMATION;
	}
	private static String deleteStudent(StudentComponent studentsList, String studentId) {
		for (int i = 0; i < studentsList.vStudent.size(); i++) {
			Student student = (Student) studentsList.vStudent.get(i);
			if (student.match(studentId) && studentsList.vStudent.remove(student)) return studentId+Constants.DELETE_STUDENT;
		} return Constants.NO_STUDENT_ID;
	}

	private static String registerStudent(StudentComponent studentsList, String message) {
		Student  student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			return Constants.ADD_STUDENT;
		} else
			return Constants.ALREADY_ADD_STUDENT;
	}
	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = Constants.BLANK;
		for (int j = 0; j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + Constants.LINE_BREAK;
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				Constants.START_LOG + comment + Constants.EVENT_ID + event.getEventId() + Constants.LOG_MESSAGE + event.getMessage());
	}
}
