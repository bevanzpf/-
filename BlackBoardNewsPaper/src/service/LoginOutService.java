package service;


import model.User.User;
import model.User.UserDao;
import tools.MD5Util;
import tools.RandomUtil;
import tools.ServiceException;

public class LoginOutService {
	/**
	 * 验证账户是否存在，激活并且密码正确；
	 * @param email
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public int loginbypassword(String email,String password) throws ServiceException{
		String password_digest = MD5Util.getMD5hash(password);
		UserDao userdao = new UserDao();
		String sql = "select id id ,password password,status status from users where email = ?";
		User user = userdao.find(sql, email);
		if(user != null){
			System.out.println(user);
			if(user.getStatus()!=0){
				if(user.getPassword().equals(password_digest)){
					return user.getId();
				}else{
					throw new ServiceException("密码错误");
				}
			}else{
				throw new ServiceException("未激活注册");
			}
		}else{
			throw new ServiceException("未注册");
		}
	}
	/**
	 * 生成随机字符作为cookie口令，并在数据库中存储口令的摘要。返回cookie口令，共controller生成cookie；
	 * @param email
	 * @return
	 */
	public String remenbemToken(String email){
		UserDao userdao = new UserDao();
		String rememberToken = RandomUtil.random(22);
		String rememberDigest = MD5Util.getMD5hash(rememberToken);
		
		String sql = "update users set rememberDigest = ? where email = ?";
		userdao.update(sql, rememberDigest,email);
		return rememberToken;
	}
	/**
	 * controller 解析cookie后传入 用户id，与 remember token 进行验证，返回id供controller生成session。
	 * @param id
	 * @param rememberToken
	 * @return
	 * @throws ServiceException
	 */
	public String autoLogin(String email,String rememberToken) throws ServiceException{
		UserDao userdao = new UserDao();
		String sql = "select email email,rememberDigest, rememberDigest from users where email = ?";
		User user = userdao.find(sql, email);
		String remember_digest = MD5Util.getMD5hash(rememberToken);
		System.out.println(user);
		if(user != null && user.getRememberDigest()!= null &&  user.getRememberDigest().equals(remember_digest)){
			return user.getEmail();
		}else{
			throw new ServiceException("cookie无效"); //捕获到这个异常立即跳转到登录页面
		}
	}
	public void logOut(String email){
		UserDao userdao = new UserDao();
		String sql = "update users set rememberDigest = 'NULL' where email =?"; //设置记忆摘要为null 避免自动登陆
		userdao.update(sql, email);
		
	}
	public static void main(String[] args) throws ServiceException {
		LoginOutService service = new LoginOutService();
//		try {
//			int id = service.loginbypassword("627620497@qq.com", "ygjjzh");
//			System.out.println(id);
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(service.autoLogin("627620497@qq.com", "dddss"));
		
	}
}
