package filters;

import dal.Account;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebFilter implements Filter {

    private final String adminRegex = "^\\/admin\\/.*$";
    private final String customerRegex = "^\\/account\\/(?!signin|signup|forgot|signout).*$";
    private final String forbiddenWhenSignined = "^\\/account\\/(signin|forgot|signup)$";

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        Account account = (Account) httpReq.getSession().getAttribute("accSession");
        String path = httpReq.getServletPath();
        System.out.println(path);

        if (path.toLowerCase().endsWith(".jsp") && !path.startsWith("/index.jsp")) {
            httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot access this page");
        } else if (account == null && (path.matches(adminRegex) || path.matches(customerRegex))) {
            httpResp.sendRedirect(httpReq.getContextPath() + "/account/signin");
        } else if (account != null) {
            if ((account.getCustomerID() == null && path.matches(customerRegex)) || (account.getRole() == 2 && path.matches(adminRegex))) {
                httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You have no permission to access this page");
            } else if (path.matches(forbiddenWhenSignined)) {
                httpResp.sendRedirect(httpReq.getContextPath());
            }
        }
        
        if (!httpResp.isCommitted()) {
            System.out.println("continue process");
            fc.doFilter(req, resp);
        }
    }

}
