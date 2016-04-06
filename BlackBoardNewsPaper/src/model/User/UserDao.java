package model.User;

import java.sql.Date;

import model.DAO;
import tools.MD5Util;

public class UserDao extends DAO{
	public void add(String sql,Object...args)
	{
//		String validateCode = MD5Util.getMD5hash(e_mail);
//		String password = MD5Util.getMD5hash(pass);
//		String email = e_mail;
//		int status = 0;
//	    Date  registerTime = new Date(new java.util.Date().getTime());
//	    String sql = "INSERT into users(validateCode, password, email,status,registerTime) values(?,?,?,?,?)";
//	    super.update(sql, validateCode,password,email,status,registerTime);
		super.update(sql,args);
	}
	public void delete(String email)
	{
		
	}
	public User find(String sql,Object...args)
	{
		return super.get(User.class, sql, args);
	}
	public void setActive(String email)
	{
		
	}
	public void update(String sql ,Object...args)
	{
		super.update(sql, args);
	}
	
	public static void main(String[] args) {
		UserDao userdao = new UserDao();
//		userdao.add("bevanzpf@gamil.com", "bevanzpf13456");
		String sql = "select validateCode validateCode,registerTime registerTime,"
				+ "status status from users where email = ?";
		User user = userdao.find(sql, "627620497@qq.com");
		System.out.println(user.getStatus());
	}
}
