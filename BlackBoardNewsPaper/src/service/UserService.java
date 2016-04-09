package service;

import model.User.User;
import model.User.UserDao;

public class UserService {
	public void updateProfile(User user){
		UserDao userdao = new UserDao();
		String sql ="Update users set email = ?, school =? ,grade=? ,name=? ,info=? where id=?";
		String email = user.getEmail();
		String school = user.getSchool();
		String grade = user.getGrade();
		String name = user.getName();
		String info = user.getInfo();
		int id= user.getId();
		System.out.println("userservice 开始更新id："+id+"的用户信息");
		userdao.update(sql, email,school,grade,name,info,id);
		
	}
}
