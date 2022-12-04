/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Registration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Components.Course.Course;
import Components.Student.Student;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class RegistrationMain {
	
	protected static String courseInfo = null;
	protected static String studentInfo = null;
	protected static boolean checkCourseValidation = false;
	protected static boolean checkStudentValidation = false;
	protected static String registrationInfo = null;
	
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("RegistrationMain (ID:" + componentId + ") is successfully registered...");

		RegistrationComponent registrationList = new RegistrationComponent("C:\\\\2022-2\\\\Ŭ��\\\\EB_Java\\\\src\\\\Registration.txt");
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case RegisterClass:
					registrationInfo = event.getMessage();
					eventBus.sendEvent(new Event(EventId.CheckStudent, event.getMessage()));
					eventBus.sendEvent(new Event(EventId.CheckCourse, event.getMessage()));
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerClass(registrationList)));
					break;
				case StudentInfoForRegistration:
					checkStudentValidation = true;
					printLogEvent("Get", event);
					studentInfo = event.getMessage();
					break;
				case CourseInfoForRegistration:
					checkCourseValidation = true;
					printLogEvent("Get", event);
					courseInfo = event.getMessage();
//					eventBus.sendEvent(new Event(EventId.ClientOutput, registerClass(registrationList)));
					break;
				case QuitTheSystem:
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
				if(checkStudentValidation==true && checkCourseValidation==true) eventBus.sendEvent(new Event(EventId.ClientOutput, registerClass(registrationList)));
			}
		}
	}
	// event�� ���� student���� student���� üũ, course���� course���� üũ, �޾ƿ� ���� �������� registration���� �������� üũ
	private static String registerClass(RegistrationComponent registrationList) {
		
		Registration registration = new Registration(registrationInfo);
		if(studentInfo==null) return "�������� �ʴ� �л��Դϴ�.";
		if(courseInfo==null) return "�������� �ʴ� �����Դϴ�.";
		Course course = new Course(courseInfo);
		Student student = new Student(studentInfo);
		ArrayList<String> prerequisiteCourses = course.getPrerequisiteCoursesList();
		ArrayList<String> completedCourses = student.getCompletedCourses();
		for(int i=0; i<prerequisiteCourses.size(); i++) {
			if(!prerequisiteCourses.contains(completedCourses.get(i))) return "���̼� ������ �����ϼ���.";
		}
		registrationList.vRegistration.add(registration);
		return "������û�� �����߽��ϴ�.";
	}

	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
