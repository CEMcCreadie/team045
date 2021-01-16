package Models.UserAccounts.User;

public enum UserType {

	ADMIN("Admin"),
	REGISTRAR("Registrar"),
	TEACHER("Teacher"),
	STUDENT("Student");

	private String userType;

	UserType(String userType) {
		this.userType = userType;
	}

	public String getEmployeeRoleName() {
		return userType;
	}

	public static UserType[] getAllUserTypes(){
		UserType[] array = {ADMIN,REGISTRAR,TEACHER,STUDENT};
		return array;
	}

}

