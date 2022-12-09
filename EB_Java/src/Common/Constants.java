package Common;

public class Constants {

	// file name
	public static final String STUDENT_SOURCE_FILE_NAME = "Students.txt";
	public static final String COURSE_SOURCE_FILE_NAME = "Courses.txt";
	public static final String REGISTRATION_SOURCE_FILE_NAME = "Registration.txt";
	
	// Log
	public static final String LOG_COMMENT_GET = "Get";
	public static final String START_LOG = "\\n**";
	public static final String EVENT_ID = " the event(ID:";
	public static final String LOG_MESSAGE = ") message: ";
	
	// message
	public static final String QUIT_SYSTEM = "Quit the system!!!";
	public static final String SEND_EVENT_ID = "\n** Sending an event(ID:";
	public static final String CLIENT_INPUT_MAIN = "** ClientInputMain(ID:";
	public static final String CLIENT_OUTPUT_MAIN = "** ClientOutputMain (ID:";
	public static final String COURSE_MAIN = "CourseMain (ID:";
	public static final String REGISTRATION_MAIN = "RegistrationMain (ID:";
	public static final String STUDENT_MAIN = "** StudentMain(ID:";
	public static final String SUCCESFULLY_REGISTERED = "is successfully registered. \n";
	public static final String MESSAGE = "\n ** Message: ";
	public static final String DELETE_COURSE = " 과목을 삭제했습니다.";
	public static final String ADD_COURSE = "This course is successfully added.";
	public static final String REGISTER_CLASS = "수강신청에 성공했습니다.";
	public static final String DELETE_STUDENT = " 학생을 삭제했습니다.";
	public static final String ADD_STUDENT = "This student is successfully added.";
	
	// input
	public static final String INPUT_STUDENT_ID = "\nEnter student ID and press return (Ex. 20131234)>> ";
	public static final String INPUT_FAMILY_NAME = "\nEnter family name and press return (Ex. Hong)>> ";
	public static final String INPUT_FIRST_NAME = "\nEnter first name and press return (Ex. Gildong)>> ";
	public static final String INPUT_DEPARTMENT = "\nEnter department and press return (Ex. CS)>> ";
	public static final String INPUT_COMPLETED_COURSE_ID = "\nEnter a list of IDs (put a space between two different IDs) of the completed courses and press return >> \n (Ex. 17651 17652 17653 17654)";
	public static final String INPUT_COURSE_ID = "\nEnter course ID and press return (Ex. 12345)>> ";
	public static final String INPUT_INSTRUCTOR_FAMILY_NAME = "\nEnter the family name of the instructor and press return (Ex. Hong)>> ";
	public static final String INPUT_COURSE_NAME = "\nEnter the name of the course ( substitute a space with ab underbar(_) ) and press return (Ex. C++_Programming)>> ";
	public static final String INPUT_PREREQUISITE_ID = "\nEnter a list of IDs (put a space between two different IDs) of prerequisite courses and press return >> \n (Ex. 12345 17651)";
	
	// menu
	public static final String INITIAL_MENU_1 = "1. List Students";
	public static final String INITIAL_MENU_2 = "2. List Courses";
	public static final String INITIAL_MENU_3 = "3. Register a new Student";
	public static final String INITIAL_MENU_4 = "4. Register a new Course";
	public static final String INITIAL_MENU_5 = "5. Delete a Student";
	public static final String INITIAL_MENU_6 = "6. Delete a Course";
	public static final String INITIAL_MENU_7 = "7. Class Registration";
	public static final String INITIAL_MENU_0 = "0. Quit the system";
	public static final String INITIAL_MENU_CHOOSE = "\n Choose No.: ";
	
	// common
	public static final String NO_INFORMATION = "noInfo";
	public static final String SEPARATOR = "/";
	public static final String EVENT_BUS = "EventBus";
	public static final String LINE_BREAK = "\n";
	public static final String BLANK = "";
	public static final String SPACING = " ";
	public static final String PARENTHESIS = ")";
	
	// exception
	public static final String NO_COURSE_ID = "존재하지 않는 과목입니다.";
	public static final String ALREADY_ADD_COURSE = "This course is already registered.";
	public static final String NO_STUDENT_ID = "존재하지 않는 학생입니다.";
	public static final String HAVE_TO_REGISTER_PREREQUISITE_COURSE = "선이수 과목을 수강하세요.";
	public static final String FAIL_REGISTER_CLASS = "수강신청에 실패했습니다.";
	public static final String ALREADY_ADD_STUDENT = "This student is already registered.";
	
	
	
}
