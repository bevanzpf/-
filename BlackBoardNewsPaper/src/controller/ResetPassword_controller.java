package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ResetPasswordService;
import tools.ServiceException;

/**
 * Servlet implementation class ResetPassword_controller
 */
@WebServlet("/ResetPassword_controller")
public class ResetPassword_controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword_controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String step = request.getParameter("step");
		ResetPasswordService  service = new ResetPasswordService();
		if(step.equals("sendResetEmail")){
			boolean success = true;
			try {
				service.sendResetEmail(email);
			} catch (ServiceException e) {
				success = false;
				request.getSession().setAttribute("message", e.getMessage());
				response.sendRedirect("ResetPassword.jsp");
//				request.setAttribute("message", e.getMessage());
//				request.getRequestDispatcher("ResetPassword.jsp").forward(request,response); //��ӡ������Ϣ
			}
			if(success){
				response.sendRedirect("SendResetSuccess.jsp");//��ʾ�ѷ��ʼ������ط��ʼ���ť// ֱ����javascript ������alter������
			}
		}
		if(step.equals("validateReset")){
			String validateCode = request.getParameter("validateCode");
			String newPassword = request.getParameter("newPassword");
			boolean success = true;
			try {
				service.validateReset(email, validateCode, newPassword);
			} catch (ServiceException e) {
				success = false;
				request.setAttribute("message", e.getMessage());
				request.getRequestDispatcher("WriteNewPassword.jsp").forward(request, response); //��ʾ �� ��д newpassword //��������������Ϣ�ķ�ʽ���а�
			}
			if(success){
				response.sendRedirect("ResetSuccess.jsp");  //javascript ���� Ȼ��ת������ҳ������ //��ʾ �ɹ��޸��������¼��ť��
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
