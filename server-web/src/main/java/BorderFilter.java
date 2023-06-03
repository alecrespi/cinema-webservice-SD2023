import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*  Ale Cre:
*   This code is a FILTER, a kind-of interceptor of all inbound request or outbound response
*   used to perform some global-standard operation on every target.
*   You are able to perform multiples Filters by invoking
*   chain.doFilter method, not calling it will let the filter quit execution without
*   continue the filter chain. This is useful when you want to suppress
*   a specific kind of requests (e.g. (not in this context) a request that
*   not contains any credentials or authentication token either ).
* */
@WebFilter("/*")
public class BorderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // Initialization code (if needed)
    }

    @Override
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) _req;
        HttpServletResponse res = (HttpServletResponse) _res;
        // Code to intercept the request before it reaches the servlets
        // Perform any necessary pre-processing
        // Code here:
        System.out.println("~~> " + req.getMethod() + " " + req.getRequestURI());

        chain.doFilter(req, res);  // Pass the request down the filter chain

        // Code to intercept the response after it has been processed by the servlets
        // Perform any necessary post-processing
        // code here:
        System.out.println("<~~ " + res.getStatus() + " " + req.getRequestURI());
    }

    @Override
    public void destroy() {
        // Cleanup code (if needed)
    }
}