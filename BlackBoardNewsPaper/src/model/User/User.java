package model.User;

import java.util.Date;
import java.util.Calendar;

import model.DAO;

public class User {
		@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", status="
				+ status + ", validateCode=" + validateCode + ", registerTime=" + registerTime + ", rememberDigest="
				+ rememberDigest + "]";
	}


		public User(){
			
		}
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
		
		
	
//		@Override
//		public String toString() {
//			return "User [id=" + id + ", name=" + name + ", password=" + password + "]";
//		}


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
	
	
		public static void addUser(String name,String password){
			DAO dao = new DAO();
			String sql = "INSERT into users(name,password) values(?,?)";
			dao.update(sql,name,password);
		}
}
