package Common;

import Components.Course.Course;
import Components.Registration.Registration;
import Components.Student.Student;

public class RegistrationData {
	
	protected Registration registrationInfo;
	protected Student studentInfo;
	protected Course courseInfo;
	
	public RegistrationData(String message) {
		String[] split = message.split(Constants.SEPARATOR);
		this.registrationInfo = new Registration(split[0]);
		if(!split[1].equals(Constants.NO_INFORMATION)) this.studentInfo = new Student(split[1]);
		else this.studentInfo=null;
		if(!split[2].equals(Constants.NO_INFORMATION)) this.courseInfo = new Course(split[2]);
		else this.courseInfo=null;
	}

	public Registration getRegistrationInfo() {
		return registrationInfo;
	}
	
	public Student getStudentInfo() {
		return studentInfo;
	}

	public Course getCourseInfo() {
		return courseInfo;
	}


}
