package Components.Util;

import java.util.StringTokenizer;

public class RegistrationRequest {
	protected String studentId;
	protected String courseId;
    
    public RegistrationRequest(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
        this.courseId = stringTokenizer.nextToken();
        this.studentId = stringTokenizer.nextToken();
    }
    public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
    public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public boolean match(String courseId) {
        return this.courseId.equals(courseId);
    }
    public String getString() {
        String stringReturn = this.courseId + " " + this.studentId;
        return stringReturn;
    }
}
