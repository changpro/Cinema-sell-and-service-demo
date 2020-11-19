package cinema.servlet;

import cinema.bean.User;
import cinema.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class addUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String pw = req.getParameter("password");
        User user = new User();
        user.setName(name);
        user.setPassword(pw);
        UserDao dao = new UserDao();
        boolean flag = dao.addUser(user);
    }
}