package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import service.FileUploadService;

/**
 * Servlet implementation class FileUpload_controller
 */
@WebServlet("/FileUpload_controller")
public class FileUpload_controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload_controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		FileUploadService service = new FileUploadService();
		if(action.equals("uploadIcon")){
			boolean success = true;
			try {
				service.proccessUploadIcon(request);
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
				request.getSession().setAttribute("message", e.getMessage());
				request.getRequestDispatcher("/a/user/index.jsp").forward(request, response);
			}
			if(success){
				response.sendRedirect("/BlackBoardNewsPaper/a/user/index.jsp");
			}
		}
		if(action.equals("uploadWork")){
			boolean success = true;
			try {
				service.processUploadWork(request);
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
				request.getSession().setAttribute("message", e.getMessage());
				response.sendRedirect("/BlackBoardNewsPaper/a/user/upload");
			}
			if(success){
				request.getSession().setAttribute("message", "上传成功");
				response.sendRedirect("/BlackBoardNewsPaper/a/user/upload");
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
