package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.User;
import service.UserService;

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
		String action = request.getParameter("action");
		UserService service = new UserService();
		if(action.equals("updateProfile")){
			User user = (User)request.getSession().getAttribute("user");
			user.setEmail(request.getParameter("email"));
			user.setGrade(request.getParameter("grade"));
			user.setSchool(request.getParameter("school"));
			user.setName(request.getParameter("name"));
			user.setInfo(request.getParameter("info"));
			service.updateProfile(user);
			response.sendRedirect("a/user/index.jsp");
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
