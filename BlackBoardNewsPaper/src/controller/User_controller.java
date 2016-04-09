package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.User;
import service.UserService;
import tools.ServiceException;

/**
 * Servlet implementation class User_controller
 */
@WebServlet("/User_controller")
public class User_controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User_controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		UserService service = new UserService();
		if(action.equals("updateProfile")){
			System.out.println("user_controller 开始获取参数");
			User user = (User)request.getSession().getAttribute("user");
			String name = request.getParameter("name");
			String school = request.getParameter("school");
			String grade = request.getParameter("grade");
			String info = request.getParameter("info");
			boolean success = true;
			try {
				service.updateProfile(user, name, school, grade, info);
			} catch (ServiceException e) {
				success = false;
				request.getSession().setAttribute("message", e.getMessage());
				response.sendRedirect("a/user/index.jsp");
			}
			if(success){
				response.sendRedirect("a/user/index.jsp");
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
