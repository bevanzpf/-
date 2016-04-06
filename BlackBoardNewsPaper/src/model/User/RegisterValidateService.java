package model.User;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import model.MD5Util;
import model.SendEmail;
import tools.ServiceException;

public class RegisterValidateService {
	private UserDao userdao ;
	
	public void processregister(String email,String password) throws ServiceException
	{
		userdao = new UserDao();
		//看是否已被注册
		String selectUser= "select email,email from users where email = ?";
		User user = userdao.find(selectUser, email);
		if(user != null){
			throw new ServiceException("email已存在，请登录或找回密码");
		}else{
		//
		String passwordDigest = MD5Util.getMD5hash(password);
		String validateCode = MD5Util.getMD5hash(email);
		int status = 0;
		java.sql.Date  registerTime = new java.sql.Date(new java.util.Date().getTime());
		
		String sql = "INSERT into users(validateCode, password, email,status,registerTime) values(?,?,?,?,?)";
		userdao.add(sql, validateCode, passwordDigest, email, status, registerTime);
		
		StringBuffer sb = new StringBuffer("欢迎使用xxx,点击下面链接激活账号，"
				+ "48小时生效，否则重新注册");
		sb.append("<a href=\"http://localhost:8080/BlackBoardNewsPaper/SignIn_controller?action=activate&email=");
		sb.append(email);
		sb.append("&validateCode=");
		String getValidateCode = "select validateCode from users where email = ?";
		sb.append(userdao.getForValue(getValidateCode, email).toString());
		sb.append("\">");
		sb.append("点击这里确定");
		sb.append("</a>");
		
		SendEmail.send(email, sb.toString(),"用户注册");
		System.out.println("发送邮件");
		}
//		String validateCode = userdao.getForValue(sql, email);
//		System.out.println(validateCode);
	}
	public void resentValidateEmail(String email){
		java.sql.Date current = new java.sql.Date(new java.util.Date().getTime());
		
		String resetRegistTime = "upate users set registerTime = ?";
		userdao = new UserDao();
		userdao.update(resetRegistTime, email);//重发验证邮件时重置注册时间。
		
		
		StringBuffer sb = new StringBuffer("欢迎使用xxx,点击下面链接激活账号，"
				+ "48小时生效，否则重新注册");
		sb.append("<a href=\"http://localhost:8080/BlackBoardNewsPaper/SignIn_controller?action=activate&email=");
		sb.append(email);
		sb.append("&validateCode=");
		
		String sql = "select validateCode from users where email = ?";
		
		sb.append(userdao.getForValue(sql, email).toString());
		sb.append("\">");
		sb.append("点击这里确定");
		sb.append("</a>");
		
		SendEmail.send(email, sb.toString(),"用户注册");
		System.out.println("发送邮件");
	}
	public void processActivate(String email,String validateCode)throws ServiceException,ParseException{
		userdao = new UserDao();
		String sql1 = "select registerTime registerTime,validateCode validateCode,status status from users where email = ?";
		User user = userdao.find(sql1,email);
		System.out.println(user.getRegisterTime());
		System.out.println(user.getValidateCode());
		if(user != null){
			
			if(user.getStatus()==0){
				Date currentTime = new Date();
				Date register_time = user.getRegisterTime();
				
				if(currentTime.after(register_time )&& currentTime.before(user.getLastActiveTime())){
					if(validateCode.equals(user.getValidateCode())){
						System.out.println("==ssq==="+user.getStatus());
						String sql = "update users set status = ? where email = ?";
						userdao.update(sql, 1,email);
						user = userdao.find(sql1,email);
						System.out.println(user.getStatus());
					}else{
						throw new ServiceException("激活码不正确");
					}
				}else{
					throw new ServiceException("激活码已过期");
				}
			}else{
				throw new ServiceException("邮箱已激活");
			}
		}else{
			throw new ServiceException("该邮箱未注册（邮箱地址不存在）");
		}
	
	}
	
	public static void main(String[] args) throws ServiceException, ParseException {
		RegisterValidateService  service= new RegisterValidateService();
//		service.reSetPasswordStep1("627620497@qq.com");
//		service.processregister("bevanzpf@gmail.com", "ygjjzh147569");
//		service.processActivate("627620497@qq.com", "36144deb116633e07a55775e1eb12e84");
	}
}
