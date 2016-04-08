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

import model.User.User;
import service.LoginOutService;
import tools.ServiceException;

/**
 * Servlet Filter implementation class WhetherAutoLogin
 */
@WebFilter("/")
public class AccessFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AccessFilter() {
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
		System.out.println("access finlter runing...");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String uri = req.getRequestURI(); //不能自动登陆时记录请求的URI；在login Controller 中重定向并销毁 。(行不通） 因为cookie还没到
		//试试在welcom.jsp 中处理(bego!)<but write code in page is ugly> 
		String root = req.getContextPath();
		if(req.getSession().getAttribute("user") == null){
			//没有用户 则记录之前请求的uri 然后跳转到登录
			req.getSession().setAttribute("before_filter", uri);
			res.sendRedirect(root +"/Login.jsp");
		}else{
			chain.doFilter(request, response);
		}
		
			
//		}else{
//			System.out.println("filter cookie 验证失败2");
//			req.getSession().setAttribute("before_filter", uri);
//			res.sendRedirect(root +"/Login.jsp");
//		}
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
