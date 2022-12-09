/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.ClientOutput;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Common.Constants;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class ClientOutputMain {
	public static void main(String[] args) throws RemoteException, IOException, NotBoundException {
		RMIEventBus eventBusInterface = (RMIEventBus) Naming.lookup(Constants.EVENT_BUS);
		long componentId = eventBusInterface.register();
		System.out.println(Constants.CLIENT_OUTPUT_MAIN + componentId + Constants.PARENTHESIS + Constants.SUCCESFULLY_REGISTERED);
		
		Event event = null;
		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBusInterface.getEventQueue(componentId);
			int size = eventQueue.getSize();
			for (int i = 0; i < size; i++) {
				event = eventQueue.getEvent();
				if (event.getEventId() == EventId.ClientOutput) {
					printOutput(event);
				} else if (event.getEventId() == EventId.QuitTheSystem) {
					//printLogReceive(event);
					eventBusInterface.unRegister(componentId);
					done = true;
				}
			}
		}
	}
	private static void printOutput(Event event) {
		System.out.println(event.getMessage());
	}
}
