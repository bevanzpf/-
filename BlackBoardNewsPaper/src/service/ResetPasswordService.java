package service;

import java.util.Date;

import model.User.User;
import model.User.UserDao;
import tools.MD5Util;
import tools.RandomUtil;
import tools.ServiceException;

public class ResetPasswordService{
	UserDao userdao;
	public void sendResetEmail(String email) throws ServiceException{
		userdao = new UserDao();
		
		String sql ="select email,email from users where email=?"; 
		
		User user = userdao.find(sql, email);
		if(user != null){
			String validate_token = RandomUtil.random(22);
			String passwordValidateCode = MD5Util.getMD5hash(validate_token);
			java.sql.Date current = new java.sql.Date(new java.util.Date().getTime());
			
			String insertPasswordValidateCode = "update users set passwordValidateCode = ?, resetTime = ? where email =?";
			
			userdao.update(insertPasswordValidateCode, passwordValidateCode,current,email);  //数据库记录 随机验证码摘要
			
			StringBuffer sb = new StringBuffer("尊敬的用户，您的申请已接收，点击链接可以修改您的密码，");
			sb.append("<a href=\"http://localhost:8080/BlackBoardNewsPaper/WriteNewPassword.jsp?email=");
			sb.append(email);
			sb.append("&validateCode=");
			sb.append(passwordValidateCode);
			sb.append("\">");
			sb.append("点击这里确定");
			sb.append("</a>");
			
			SendEmail.send(email, sb.toString(),"密码重置");
			System.out.println("发送重置密码邮件");
			
			//把 remember Digest 设置为null;
			String sql2 = " update users set rememberDigest = 'null'  where email= ?";
			userdao.update(sql2, email);
			System.out.println("rememberDigest已修改");
		}else{
			throw new ServiceException("非用户，请注册");
		}
	}
	public void validateReset(String email,String validateCode,String newPassword) throws ServiceException{
		userdao = new UserDao();
		
		String sql = "Select passwordValidateCode passwordValidateCode,resetTime resetTime from users where email = ?";
		
		User user = userdao.find(sql, email);
		if(user != null){
			Date current = new Date();
			Date resetTime = user.getResetTime();
			if(current.after(resetTime )&& current.before(user.getLastResetTime())){
				if(user.getPasswordValidateCode().equals(validateCode)){
					resetPassword(newPassword,email);  
				}else{
					throw new ServiceException("验证错误");
				}
			}else{
				throw new ServiceException("链接已过期");
			}
		}else{
			throw new ServiceException("用户不合法");
		}
	}
	private void resetPassword(String newPassword,String email) {
		String sql = "Update users set password = ? where email = ?";
		userdao = new UserDao();
		String newPasswordDigest = MD5Util.getMD5hash(newPassword);
		userdao.update(sql, newPasswordDigest,email);
		System.out.println("密码修改成功");
	}
	public static void main(String[] args) throws ServiceException {
		ResetPasswordService reset = new ResetPasswordService();
		reset.sendResetEmail("620");
//		reset.step2("627620497@qq.com", "99cfa9fd2635641c6f0f1101bd0a0b83", "gggggggg");
	}
}
