package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {
    public MyFilter() {
        System.out.println("Object Created from MyFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.println("My Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //Before the request send
        System.out.println("First");
        HttpServletResponse resp= (HttpServletResponse) servletResponse;

        filterChain.doFilter(servletRequest, servletResponse); // proceed request to the servlet
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "DELETE, PUT");
        resp.addHeader("Access-Control-Allow-Headers", "content-Type");
        resp.setContentType("application/json");

        //After the servlet response
        System.out.println("Second");

    }

    @Override
    public void destroy() {
        System.out.println("Destroy method invoked");

    }
}
