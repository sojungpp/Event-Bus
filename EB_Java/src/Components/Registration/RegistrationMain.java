/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Registration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import Components.Common.RegistrationData;
import Components.Course.Course;
import Components.Student.Student;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class RegistrationMain {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("RegistrationMain (ID:" + componentId + ") is successfully registered...");

		RegistrationComponent registrationList = new RegistrationComponent("C:\\\\2022-2\\\\클서\\\\EB_Java\\\\src\\\\Registration.txt");
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
					printLogEvent("Get", event);
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
	// event를 통해 student에서 student유무 체크, course에서 course유무 체크, 받아온 것을 바탕으로 registration에서 선수과목 체크
	private static String registerClass(RegistrationComponent registrationList, String message) {
		System.out.println("registerClass: " + message);
		RegistrationData registrationData = new RegistrationData(message);
		if(registrationData.getStudentInfo()==null) return "존재하지 않는 학생입니다.";
		if(registrationData.getCourseInfo()==null) return "존재하지 않는 과목입니다.";
		ArrayList<String> prerequisiteCourses = registrationData.getCourseInfo().getPrerequisiteCoursesList();
		ArrayList<String> completedCourses = registrationData.getStudentInfo().getCompletedCourses();
		for(int i=0; i<prerequisiteCourses.size(); i++) {
			if(!prerequisiteCourses.contains(completedCourses.get(i))) return "선이수 과목을 수강하세요.";
		}
		if(!registrationList.vRegistration.add(registrationData.getRegistrationInfo())) return "수강신청에 실패했습니다.";
		return "수강신청에 성공했습니다.";
	}

	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}
}
