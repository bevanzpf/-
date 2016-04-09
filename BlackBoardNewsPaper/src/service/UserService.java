package service;

import model.User.User;
import model.User.UserDao;
import tools.ServiceException;

public class UserService {
	public void updateProfile(User user,String name,String school,String grade,String info) throws ServiceException{
		UserDao userdao = new UserDao();
		int id = user.getId();
		user.setSchool(school);
		user.setGrade(grade);
		user.setInfo(info);
		String update3Field = "update users set school=?, grade =?, info=? where id=?";
		userdao.update(update3Field, school,grade,info,id);
		
		//validate new name (whether it had been used)
		if(!name.equals(user.getName())){
			String validateNewName ="Select id from users where name = ?";
			User another = userdao.find(validateNewName,name);
			if(another != null){
				throw new ServiceException("昵称： "+name+"被占用");
			}else{
				user.setName(name);
				String updateName = "update users set name =? where id = ?";
				userdao.update(updateName, name,id );
			}
		}
//		String sql ="Update users set email = ?, school =? ,grade=? ,name=? ,info=? where id=?";
//		String email = user.getEmail();
//		String school = user.getSchool();
//		String grade = user.getGrade();
//		String name = user.getName();
//		String info = user.getInfo();
//		int id= user.getId();
//		System.out.println("userservice 开始更新id："+id+"的用户信息");
//		userdao.update(sql, email,school,grade,name,info,id);
		
	}
}
