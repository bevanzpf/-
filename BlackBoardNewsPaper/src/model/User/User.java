package model.User;

import java.util.Date;
import java.util.Calendar;

import model.DAO;

public class User {
	private int id;
	private String name;
	private String password;
	private String email;
	private int status; //whether it is jihuo
	private String validateCode;
	private Date registerTime;
	private String rememberDigest;
	private String passwordValidateCode;
	private Date resetTime;
	private String school;
	private String grade;
	private String info;
	private String iconURL;
	
	

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String icon) {
		this.iconURL = icon;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

		@Override
		public String toString() {
			return "User [name=" + name + ", email=" + email + "]";
		}

		public User(){
			
		}
		
		public String getPasswordValidateCode() {
			return passwordValidateCode;
		}


		public void setPasswordValidateCode(String passwordValidateCode) {
			this.passwordValidateCode = passwordValidateCode;
		}


		public Date getResetTime() {
			return resetTime;
		}


		public void setResetTime(Date resetTime) {
			this.resetTime = resetTime;
		}


		public String getRememberDigest() {
			return rememberDigest;
		}


		public void setRememberDigest(String rememberToken) {
			this.rememberDigest = rememberToken;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public int getStatus() {
			return status;
		}


		public void setStatus(int status) {
			this.status = status;
		}


		public String getValidateCode() {
			return validateCode;
		}


		public void setValidateCode(String validateCode) {
			this.validateCode = validateCode;
		}


		public Date getRegisterTime() {
			return registerTime;
		}


		public void setRegisterTime(Date registerTime) {
			this.registerTime = registerTime;
		}

		public int getId() {
			return id;
		}
	
	
		public void setId(int id) {
			this.id = id;
		}
	
	
		public String getName() {
			return name;
		}
	
	
		public void setName(String name) {
			this.name = name;
		}
	
	
		public String getPassword() {
			return password;
		}
	
	
		public void setPassword(String password) {
			this.password = password;
		}
		public Date getLastActiveTime(){
			Calendar c1 = Calendar.getInstance();
			c1.setTime(registerTime);
			c1.add(Calendar.DATE, 2);
			return c1.getTime();
		}
		public Date getLastResetTime(){
			Calendar c1 = Calendar.getInstance();
			c1.setTime(resetTime);
			c1.add(Calendar.DATE, 2);
			return c1.getTime();
		}
}
