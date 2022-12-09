/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.ClientInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Common.Constants;
import Framework.Event;
import Framework.EventId;
import Framework.RMIEventBus;

public class ClientInputMain {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(Constants.EVENT_BUS);
		long componentId = eventBus.register();
		System.out.println(Constants.CLIENT_INPUT_MAIN + componentId + Constants.PARENTHESIS + Constants.SUCCESFULLY_REGISTERED);
		
		boolean done = false;
		while (!done) {
			writeMenu();
			try {
				switch (new BufferedReader(new InputStreamReader(System.in)).readLine().trim()) {
				case "1":
					eventBus.sendEvent(new Event(EventId.ListStudents, null));
					printLogSend(EventId.ListStudents);
					break;
				case "2":
					eventBus.sendEvent(new Event(EventId.ListCourses, null));
					printLogSend(EventId.ListCourses);
					break;
				case "3":
					eventBus.sendEvent(new Event(EventId.RegisterStudents, makeStudentInfo()));
					printLogSend(EventId.RegisterStudents);
					break;
				case "4":
					eventBus.sendEvent(new Event(EventId.RegisterCourses, makeCourseInfo()));
					printLogSend(EventId.RegisterCourses);
					break;
				case "5":
					eventBus.sendEvent(new Event(EventId.DeleteStudents, deleteStudentInfo()));
					printLogSend(EventId.DeleteStudents);
					break;
				case "6":
					eventBus.sendEvent(new Event(EventId.DeleteCourses, deleteCourseInfo()));
					printLogSend(EventId.DeleteCourses);
					break;
				case "7":
					eventBus.sendEvent(new Event(EventId.StudentInfoForRegistration, registerClass()));
					printLogSend(EventId.StudentInfoForRegistration);
					break;
				case "0":
					eventBus.sendEvent(new Event(EventId.QuitTheSystem, Constants.QUIT_SYSTEM));
					printLogSend(EventId.QuitTheSystem);
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static String makeStudentInfo() throws IOException {
		String userInput = Constants.BLANK;
		System.out.println(Constants.INPUT_STUDENT_ID);
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_FAMILY_NAME);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_FIRST_NAME);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_DEPARTMENT);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_COMPLETED_COURSE_ID);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.MESSAGE + userInput + Constants.LINE_BREAK);
		return userInput;
	}
	private static String makeCourseInfo() throws IOException {
		String userInput = Constants.BLANK;
		System.out.println(Constants.INPUT_COURSE_ID);
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_INSTRUCTOR_FAMILY_NAME);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_COURSE_NAME);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_PREREQUISITE_ID);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.MESSAGE + userInput + Constants.LINE_BREAK);
		return userInput;
	}
	private static String deleteStudentInfo() throws IOException {
		String userInput = Constants.BLANK;
		System.out.println(Constants.INPUT_STUDENT_ID);
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.MESSAGE + userInput + Constants.LINE_BREAK);
		return userInput;
	}
	private static String deleteCourseInfo() throws IOException {
		String userInput = Constants.BLANK;
		System.out.println(Constants.INPUT_COURSE_ID);
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.MESSAGE + userInput + Constants.LINE_BREAK);
		return userInput;
	}
	private static String registerClass() throws IOException {
		String userInput = Constants.BLANK;
		System.out.println(Constants.INPUT_STUDENT_ID);
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.INPUT_COURSE_ID);
		userInput += Constants.SPACING + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(Constants.MESSAGE + userInput + Constants.LINE_BREAK);
		return userInput;
	}
	@SuppressWarnings("unused")
	private static String setStudentId() throws IOException {
		System.out.println(Constants.INPUT_STUDENT_ID);
		return new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	}
	@SuppressWarnings("unused")
	private static String setCourseId() throws IOException {
		System.out.println(Constants.INPUT_COURSE_ID);
		return new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	}
	private static void writeMenu() {
		System.out.println(Constants.INITIAL_MENU_1);
		System.out.println(Constants.INITIAL_MENU_2);
		System.out.println(Constants.INITIAL_MENU_3);
		System.out.println(Constants.INITIAL_MENU_4);
		System.out.println(Constants.INITIAL_MENU_5);
		System.out.println(Constants.INITIAL_MENU_6);
		System.out.println(Constants.INITIAL_MENU_7);
		System.out.println(Constants.INITIAL_MENU_0);
		System.out.print(Constants.INITIAL_MENU_CHOOSE);
	}
	private static void printLogSend(EventId eventId) {
		System.out.println(Constants.SEND_EVENT_ID + eventId + Constants.PARENTHESIS + Constants.LINE_BREAK);
	}
}
