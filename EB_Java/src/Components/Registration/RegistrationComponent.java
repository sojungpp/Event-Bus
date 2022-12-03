package Components.Registration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class RegistrationComponent {
	protected ArrayList<Registration> vRegistration;
	
	public RegistrationComponent(String sRegistrationFileName) throws FileNotFoundException, IOException { 	
		BufferedReader bufferedReader = new BufferedReader(new FileReader(sRegistrationFileName));
		this.vRegistration = new ArrayList<Registration>();
		while (bufferedReader.ready()) {
			String registrationInfo = bufferedReader.readLine();
			if (!registrationInfo.equals("")) this.vRegistration.add(new Registration(registrationInfo));
		}
		bufferedReader.close();
	}
	
	public ArrayList<Registration> getRegistrationList() {
		return vRegistration;
	}
	public boolean isRegisteredClass(String studentId) {
		for (int i = 0; i < this.vRegistration.size(); i++) {
			if (((Registration) this.vRegistration.get(i)).match(studentId)) return true;
		} return false;
	}
}
