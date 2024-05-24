package account.operations.deposit.servlet;

import account.operations.credit.database.CreditDAO;
import account.operations.deposit.database.DepositDAO;
import account.servlet.CreateAccountServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import user.database.User;
import utils.fileutil.LoggerUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/createDepositServlet")
public class CreateDepositServlet extends HttpServlet {
    private static Logger logger;
    static
    {
        logger = LogManager.getLogger(CreateAccountServlet.class);
        LoggerUtils.setLogger(logger);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("start CreateDepositServlet🚀");

        HttpSession currentUserSession = req.getSession();
        User currentUser = (User) currentUserSession.getAttribute("currentUserSession");

        Cookie[] cookies = req.getCookies();
        int currentAccountIndex = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("currentAccountIndex")) {
                    currentAccountIndex = Integer.parseInt(cookie.getValue());
                    break;
                }
            }
        }

        String amount = req.getParameter("amount");
        String endDate = req.getParameter("end_date");
        try
        {
            DepositDAO.createDeposit(currentUser.getAccount(currentAccountIndex).getId(), Double.parseDouble(amount), endDate);
        }
        catch (Exception e)
        {
            logger.error("Creating deposit servlet error❌: " + e.getMessage());
        }
        logger.info("CreateCreditServlet finished✅");
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<html><head><script>window.opener.location.reload();window.close();</script></head></html>");
    }
}
