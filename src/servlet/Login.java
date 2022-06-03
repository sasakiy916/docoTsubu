package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.LoginLogic;
import model.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		User user = new User(name,pass);

		LoginLogic login = new LoginLogic();
		boolean isLogin = login.execute(user);

		if(isLogin) {
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/loginResult.jsp");
		rd.forward(request, response);
	}

}
