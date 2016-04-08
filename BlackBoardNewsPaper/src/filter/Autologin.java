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
 * Servlet Filter implementation class Autologin
 */
@WebFilter("/")
public class Autologin implements Filter {

    /**
     * Default constructor. 
     */
    public Autologin() {
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
		System.out.println("autologinfilter runing");
		
		HttpServletRequest req = (HttpServletRequest)request;
		if(req.getSession().getAttribute("user") == null){
			HttpServletResponse res = (HttpServletResponse)response;
			LoginOutService service;
			User user_by_check = null;
			String token = null;
			String user = null;
			Cookie[] cookies = req.getCookies();
			for(Cookie cookie : cookies){
				//真确地读cookie  String value = URLDecoder.decode(cookie.getValue(), "UTF-8");
				if(cookie.getName().equals("hei_token"))
					token = URLDecoder.decode(cookie.getValue(), "UTF-8");
				if(cookie.getName().equals("hei_user"))
					user = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
			if(token != null && user != null){
				service = new LoginOutService();
				boolean auto = true;
				try {
					user_by_check = service.autoLogin(user, token);
				} catch (ServiceException e) {
					System.out.println("autologinfilter: 验证失败");
					auto = false;
					chain.doFilter(request, response);
				}
				if(auto){
					System.out.println("auto login filter: cookie 验证成功 ");
					req.getSession(true).setAttribute("user", user_by_check);
					chain.doFilter(request, response);
				}
			}else{
				chain.doFilter(request, response);
			}
		}else{
			System.out.println("auto login filter ：已经有session[user] zhi接dofilter");
			chain.doFilter(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
