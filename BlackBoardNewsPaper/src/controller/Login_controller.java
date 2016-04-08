package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.User;
import service.LoginOutService;
import tools.ServiceException;

/**
 * Servlet implementation class Login_controller
 */
@WebServlet("/Login_controller")
public class Login_controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login_controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoginOutService  service = new LoginOutService();
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		User user = null;
		if(action != null && action.equals("logout")){
			service.logOut(email);
			HttpSession session = request.getSession();
			session.removeAttribute("user");
			response.sendRedirect("index.jsp");
		}else{
		//
			System.out.println("在Login controller获取的请求URI:"+request.getRequestURI());
			String password = request.getParameter("password");
			String keep = request.getParameter("remember_me");
			boolean seccess = true;
			// 连接验证email password
			try {
				user = service.loginbypassword(email, password);
				System.out.println("logincontroller：在loginService中验证完成");
			} catch (ServiceException e) {
				System.out.println("logincontroller: 在loginService中验证出错");
				seccess = false;
				request.getSession().setAttribute("message",e.getMessage());
				response.sendRedirect("Login.jsp");
//				request.setAttribute("message", e.getMessage());
//				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
			if(seccess){
				if(keep.equals("1")){
					
					System.out.println("logincontroller:开始写入cookie");
	//				 new Cookie(name, URLEncoder.encode(value, "UTF-8"));
					String rememberToken = service.remenbemToken(email);
					Cookie idcookie = new Cookie("hei_user",URLEncoder.encode(email, "UTF-8"));
					idcookie.setMaxAge(30*3600*24);
					Cookie remembercookie = new Cookie("hei_token",URLEncoder.encode(rememberToken, "UTF-8"));
					remembercookie.setMaxAge(30*3600*24);
					
					response.addCookie(idcookie);
					response.addCookie(remembercookie);
				}
				HttpSession session = request.getSession(true);
				session.setAttribute("user", user); //把user 对象写入session
				response.sendRedirect("index.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
