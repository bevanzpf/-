package filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LoginOutService;
import tools.ServiceException;

/**
 * Servlet Filter implementation class WhetherAutoLogin
 */
@WebFilter("/")
public class WhetherAutoLogin implements Filter {

    /**
     * Default constructor. 
     */
    public WhetherAutoLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		System.out.println("whether auto login finlter runing...");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		LoginOutService service;
		
		String token = null;
		String user = null;
		String email_by_check = null;
		String uri = req.getRequestURI(); //不能自动登陆时记录请求的URI；在login Controller 中重定向并销毁 。(行不通） 因为cookie还没到
		//试试在welcom.jsp 中处理(bego!)<but write code in page is ugly> 
		System.out.println("filter from .."+uri);
		String root = req.getContextPath();
		
		Cookie[] cookies = req.getCookies();
		
		for(Cookie cookie : cookies){
			//真确地读cookie  String value = URLDecoder.decode(cookie.getValue(), "UTF-8");
			if(cookie.getName().equals("hei_token"))
				token = URLDecoder.decode(cookie.getValue(), "UTF-8");
			if(cookie.getName().equals("hei_user"))
				user = URLDecoder.decode(cookie.getValue(), "UTF-8");
		}
		if(token !=null && user != null){
			service = new LoginOutService();
			boolean auto = true;
			try {
				email_by_check = service.autoLogin(user, token);
			} catch (ServiceException e) {
				System.out.println("filter cookie 验证失败1");
				auto = false;
				req.getSession().setAttribute("before_filter", uri);
				res.sendRedirect(root +"/Login.jsp");
			}
			if(auto){
				System.out.println("filter: cookie 验证成功 ");
				req.getSession(true).setAttribute("user", email_by_check);
				chain.doFilter(request, response);
			}
		}else{
			System.out.println("filter cookie 验证失败2");
			req.getSession().setAttribute("before_filter", uri);
			res.sendRedirect(root +"/Login.jsp");
		}
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
