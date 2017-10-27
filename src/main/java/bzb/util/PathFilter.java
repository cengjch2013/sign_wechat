package bzb.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebFilter
public class PathFilter implements Filter{

	private static List<String> ignorPath = new ArrayList<String>();
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		/*HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String url = servletRequest.getRequestURI();
		String username= servletRequest.getSession().getAttribute("username").toString().trim();
		if (ignorPath.contains(url) || !username.isEmpty() ) {
			chain.doFilter(request, response);			
		}else {			
			PrintWriter out = null;
			try {
				servletResponse.setCharacterEncoding("UTF-8");  
				servletResponse.setContentType("application/json; charset=utf-8");
				Map<String, Object> map = new HashMap<String, Object>();				
				map.put("status", "error");
				map.put("msg", "登陆超时");
				out = servletResponse.getWriter();
				JSONObject json = new JSONObject(map);
				out.append(json.toString());				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				if (out != null) {
					out.close();
				}
			}
		}*/
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		ignorPath.add("/login");
		ignorPath.add("/logout");		
		ignorPath.add("/");
	}

}
