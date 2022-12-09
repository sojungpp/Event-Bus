package Components.Registration;

import java.util.ArrayList;
import java.util.StringTokenizer;

import Common.Constants;

public class Registration {

	protected String studentId;
    protected ArrayList<String> registerCoursesList;

    public Registration(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString); 
    	this.studentId = stringTokenizer.nextToken(); 
    	this.registerCoursesList = new ArrayList<String>();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.registerCoursesList.add(stringTokenizer.nextToken());
    	}
    }
    
    public boolean match(String studentId) {
        return this.studentId.equals(studentId);
    }

    public String getString() {
        String stringReturn = this.studentId + Constants.SPACING;
        for (int i = 0; i < this.registerCoursesList.size(); i++) {
            stringReturn = stringReturn + " " + this.registerCoursesList.get(i).toString();
        }
        return stringReturn;
    }

    public ArrayList<String> getRegisterCourse() {
        return this.registerCoursesList;
    }
	public ArrayList<String> getRegisterCoursesList() {
		return registerCoursesList;
	}
	public void setRegisterCoursesList(String id) {
		this.registerCoursesList.add(id);
	}

	public String getStudentId() {
		return studentId;
	}
    
}
