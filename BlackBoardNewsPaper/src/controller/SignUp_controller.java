package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO;
import model.User.User;
import service.RegisterValidateService;
import tools.ServiceException;

/**
 * Servlet implementation class SignIn_controller
 */
@WebServlet("/SignUp_controller")
public class SignUp_controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp_controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		RegisterValidateService registerService = new RegisterValidateService();
		if(action.equals("register")){
			boolean success = true;
			try {
				registerService.processregister(email, password);
			} catch (ServiceException e) {
				success = false;
				request.getSession(true).setAttribute("message", e.getMessage());
				response.sendRedirect("SignUp.jsp");
//				request.setAttribute("message", e.getMessage());
//				request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
			}
			if(success){
				request.getSession(true).setAttribute("email", email);
				response.sendRedirect("SignUpSuccess.jsp");
//				request.setAttribute("email" , email); 
//				request.getRequestDispatcher("/SignUpSuccess.jsp").forward(request, response);
			}
			
//			response.sendRedirect("SignInSuccess.jsp");
		}
		if(action.equals("reSendEmail")){
			registerService.resentValidateEmail(email);
			response.sendRedirect("ResentSuccess.jsp");
		}
		if(action.equals("activate")){
			String validateCode = request.getParameter("validateCode");
			boolean success = true;
			try{
				try {
					registerService.processActivate(email, validateCode);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch(ServiceException e){
				success = false;
				request.getSession(true).setAttribute("message", e.getMessage());
				response.sendRedirect("SignUp.jsp");
//				request.setAttribute("message", e.getMessage());
//				request.getRequestDispatcher("/SignUp.jsp").forward(request, response);
			}
			if(success){
				request.getSession(true).setAttribute("message", "邮箱已激活，现在可以登录了");
				response.sendRedirect("Login.jsp");
//				request.setAttribute("message", "邮箱已激活，现在可以登录了");
//				request.getRequestDispatcher("/Login.jsp").forward(request, response);//？？因为用了转发所以刷新后又回到SiginSevlvert
				//被上面的 catch捕获，转到 SignIn.jsp 不要转发有怎么带信息过去呢？？？ 细化exception？？？//用户不存在，链接过期，激活码不正确。
				
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//http://localhost:8080/BlackBoardNewsPaper/SignIn_controller?action=register
		// TODOhttp://localhost:8080/BlackBoardNewsPaper/SignIn_controller?action=activate&email=627620497@qq.com&validateCode=36144deb116633e07a55775e1eb12e84 Auto-generated method stub
		doGet(request, response);
	}
	//

}
